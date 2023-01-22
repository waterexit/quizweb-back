package quizweb.domain.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import quizweb.common.enums.ChoiceTypeEnum;
import quizweb.common.properties.ApplicationProperties;
import quizweb.common.util.FileUtil;
import quizweb.domain.repository.entity.Choice;
import quizweb.domain.repository.entity.Question;
import quizweb.domain.repository.entity.Quiz;
import quizweb.domain.repository.entity.QuizTagging;
import quizweb.domain.repository.entity.Tag;
import quizweb.domain.repository.mapper.ExpandChoiceMapper;
import quizweb.domain.repository.mapper.ExpandQuestionMapper;
import quizweb.domain.repository.mapper.QuizTaggingMapper;
import quizweb.domain.repository.mapper.TagMapper;
import quizweb.domain.repository.mapper.base.QuizMapper;
import quizweb.domain.service.EditQuizService;
import quizweb.presentation.request.CreateQuizRequest.CreateQuizParam;
import quizweb.presentation.request.CreateQuizRequest.CreateQuizParam.CreateChoiceParam;
import quizweb.presentation.request.CreateQuizRequest.CreateQuizParam.CreateQuestionParam;

@Service
public class EditQuizServiceImpl implements EditQuizService{
    @Autowired
    public EditQuizServiceImpl(QuizMapper quizMapper, ExpandQuestionMapper questionMapper, ExpandChoiceMapper choiceMapper,
    ApplicationProperties applicationProperties, TagMapper tagMapper, QuizTaggingMapper quizTaggingMapper) {
        this.quizMapper = quizMapper;
        this.questionMapper = questionMapper;
        this.choiceMapper = choiceMapper;
        this.applicationProperties = applicationProperties;
        this.tagMapper = tagMapper;
        this.quizTaggingMapper = quizTaggingMapper;
    }
    
    private QuizMapper quizMapper;
    private ExpandQuestionMapper questionMapper;
    private ExpandChoiceMapper choiceMapper;
    private TagMapper tagMapper;
    private QuizTaggingMapper quizTaggingMapper;
    private ApplicationProperties applicationProperties;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editQuiz(CreateQuizParam createQuizParam) throws IOException {      
        Quiz quiz = quizMapper.selectByPrimaryKey(createQuizParam.getId());

        String fileName = FileUtil.saveImageByBase64(applicationProperties.getImageThumbnailPath(),
                createQuizParam.getThumbnail());
        quiz.setThumbnail(fileName);

        quiz.setTitle(createQuizParam.getTitle());
        quiz.setDescription(createQuizParam.getDescription());

        quizMapper.updateByPrimaryKeySelective(quiz);

        deleteRelativeTables(quiz.getId());

        List<Tag> tags = insertAndUpdateTags(createQuizParam.getTags());

        insertQuizTagging(quiz.getId(), tags);
        // パラメータが持つquestionをinsert
        insertQuestions(quiz.getId(), createQuizParam.getQuestions());

    }

    @Override
    public void publish(Long quizId) {
        Quiz quiz = new  Quiz(); 
        quiz.setId(quizId);
        quiz.setPublish(true);
        quizMapper.updateByPrimaryKeySelective(quiz);
    }

    private List<Tag> insertAndUpdateTags(List<Tag> tagList) {
        List<Tag> insertTags = tagList.stream().filter(t -> t.getId() == null).collect(Collectors.toList());
        tagMapper.insertList(insertTags);

        List<Tag> updateTags = tagList.stream().filter(t -> t.getId() != null).collect(Collectors.toList());
        for (Tag tag : updateTags)
            tagMapper.updateByPrimaryKey(tag);

        return Stream.concat(insertTags.stream(), updateTags.stream()).collect(Collectors.toList());
    }

    private void deleteRelativeTables(Long quizId){
        quizTaggingMapper.deleteByQuizId(quizId);
        questionMapper.deleteByQuizId(quizId);
        choiceMapper.deleteByQuizId(quizId);
    }

    private void insertQuizTagging(Long quizId, List<Tag> tags) {    
        List<QuizTagging> quizTaggings = new ArrayList<>();
        for (Tag tag : tags) {
            QuizTagging quizTagging = new QuizTagging();
            quizTagging.setQuizId(quizId);
            quizTagging.setTagId(tag.getId());
        }
        quizTaggingMapper.insertList(quizTaggings);
    }

    private void insertQuestions(long quizId, List<CreateQuestionParam> params) throws IOException {
        System.out.println(params);
        for (int i = 0; i < params.size(); i++) {
            CreateQuestionParam questionParam = params.get(i);
            Question question = new Question();
            question.setQuizId(quizId);
            question.setNum(i + 1);
            question.setContent(questionParam.getContent());
            question.setChoiceType(questionParam.getChoiceType().toString());
            questionMapper.insert(question);
            insertChoice(questionParam.getChoiceType(), question.getId(), questionParam.getChoices());
        }

    }

    private void insertChoice(ChoiceTypeEnum choiceType, Long questionId, List<CreateChoiceParam> params)
            throws IOException {
        switch (choiceType) {
            case single:
                insertSingleChoice(questionId, params);
                break;
            case image:
                insertImageChoice(questionId, params);
                break;
        }
    }

    private void insertSingleChoice(long questionId, List<CreateChoiceParam> params) {
        for (int i = 0; i < params.size(); i++) {
            CreateChoiceParam questionParam = params.get(i);

            long choiceId = Long.valueOf(String.valueOf(i) + String.valueOf(questionId));
            Choice choice = new Choice();
            choice.setId(choiceId);
            choice.setQuestionId(questionId);
            choice.setContent(questionParam.getContent());
            choice.setCorrectFlg(questionParam.getCorrectFlg());
            choice.setSelectionNo(i);

            choiceMapper.insert(choice);
        }

    }

    private void insertImageChoice(long questionId, List<CreateChoiceParam> params) throws IOException {
        for (int i = 0; i < params.size(); i++) {
            CreateChoiceParam questionParam = params.get(i);
            long choiceId = Long.valueOf(String.valueOf(i) + String.valueOf(questionId));
            Choice choice = new Choice();
            choice.setSelectionNo(i);
            choice.setId(choiceId);
            choice.setQuestionId(questionId);

            String fileName = FileUtil.saveImageByBase64(applicationProperties.getImageChoicePath(),
                    questionParam.getContent());
            choice.setContent(fileName);

            choice.setCorrectFlg(questionParam.getCorrectFlg());

            choiceMapper.insert(choice);

        }
    }

}
