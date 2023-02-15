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
import quizweb.domain.repository.entity.QuestionDetail;
import quizweb.domain.repository.entity.Quiz;
import quizweb.domain.repository.entity.QuizDetail;
import quizweb.domain.repository.entity.QuizTagging;
import quizweb.domain.repository.entity.Tag;
import quizweb.domain.repository.mapper.ExpandChoiceMapper;
import quizweb.domain.repository.mapper.ExpandQuestionMapper;
import quizweb.domain.repository.mapper.QuizDetailMapper;
import quizweb.domain.repository.mapper.QuizTaggingMapper;
import quizweb.domain.repository.mapper.TagMapper;
import quizweb.domain.repository.mapper.base.QuizMapper;
import quizweb.domain.service.EditQuizService;
import quizweb.object.CreateChoiceParam;
import quizweb.object.CreateQuestionParam;
import quizweb.object.CreateQuizParam;

@Service
public class EditQuizServiceImpl implements EditQuizService {
    @Autowired
    public EditQuizServiceImpl(QuizMapper quizMapper, QuizDetailMapper quizDetailMapper,
            ExpandQuestionMapper questionMapper, ExpandChoiceMapper choiceMapper,
            ApplicationProperties applicationProperties, TagMapper tagMapper, QuizTaggingMapper quizTaggingMapper) {
        this.quizMapper = quizMapper;
        this.quizDetailMapper = quizDetailMapper;
        this.questionMapper = questionMapper;
        this.choiceMapper = choiceMapper;
        this.applicationProperties = applicationProperties;
        this.tagMapper = tagMapper;
        this.quizTaggingMapper = quizTaggingMapper;
    }

    private QuizMapper quizMapper;
    private QuizDetailMapper quizDetailMapper;
    private ExpandQuestionMapper questionMapper;
    private ExpandChoiceMapper choiceMapper;
    private TagMapper tagMapper;
    private QuizTaggingMapper quizTaggingMapper;
    private ApplicationProperties applicationProperties;

    @Override
    public CreateQuizParam getEditParam(long id) {
        QuizDetail quizDetail = quizDetailMapper.getQuizDetail(id);
        CreateQuizParam ret = new CreateQuizParam();
        ret.setTitle(quizDetail.getTitle());
        ret.setDescription(quizDetail.getDescription());
        ret.setThumbnail(quizDetail.getThumbnail());
        ret.setTags(quizDetail.getTags());
        List<CreateQuestionParam> retQuestionList = new ArrayList<>();
        for (QuestionDetail question : quizDetail.getQuestions()) {
            CreateQuestionParam tmpQuestion = new CreateQuestionParam();
            tmpQuestion.setContent(question.getContent());
            tmpQuestion.setComment(question.getComment());
            tmpQuestion.setChoiceType(question.getChoiceType());
            List<CreateChoiceParam> retChoiceList = new ArrayList<>();
            for (Choice choice : question.getChoices()) {
                CreateChoiceParam tmpChoice = new CreateChoiceParam();
                tmpChoice.setContent(choice.getContent());
                tmpChoice.setCorrectFlg(choice.getCorrectFlg());
                retChoiceList.add(tmpChoice);
            }
            tmpQuestion.setChoices(retChoiceList);
            retQuestionList.add(tmpQuestion);
        }
        ret.setQuestions(retQuestionList);

        return ret;
    }

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
        Quiz quiz = new Quiz();
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

    private void deleteRelativeTables(Long quizId) {
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
