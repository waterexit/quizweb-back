package quizweb.domain.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import quizweb.common.constant.Constants;
import quizweb.common.enums.ChoiceTypeEnum;
import quizweb.common.properties.ApplicationProperties;
import quizweb.common.util.FileUtil;
import quizweb.domain.repository.entity.Choice;
import quizweb.domain.repository.entity.Question;
import quizweb.domain.repository.entity.Quiz;
import quizweb.domain.repository.entity.QuizTagging;
import quizweb.domain.repository.entity.Tag;
import quizweb.domain.repository.mapper.QuizTaggingMapper;
import quizweb.domain.repository.mapper.TagMapper;
import quizweb.domain.repository.mapper.base.ChoiceMapper;
import quizweb.domain.repository.mapper.base.QuestionMapper;
import quizweb.domain.repository.mapper.base.QuizMapper;
import quizweb.domain.service.InsertInBulkService;
import quizweb.object.CreateChoiceParam;
import quizweb.object.CreateQuestionParam;
import quizweb.object.CreateQuizParam;

@Service
public class InsertInBulkServiceImpl implements InsertInBulkService {
    @Autowired
    public InsertInBulkServiceImpl(QuizMapper quizMapper, QuestionMapper questionMapper, ChoiceMapper choiceMapper,
            ApplicationProperties applicationProperties, TagMapper tagMapper, QuizTaggingMapper quizTaggingMapper) {
        this.quizMapper = quizMapper;
        this.questionMapper = questionMapper;
        this.choiceMapper = choiceMapper;
        this.applicationProperties = applicationProperties;
        this.tagMapper = tagMapper;
        this.quizTaggingMapper = quizTaggingMapper;
    }

    private QuizMapper quizMapper;
    private QuestionMapper questionMapper;
    private ChoiceMapper choiceMapper;
    private TagMapper tagMapper;
    private QuizTaggingMapper quizTaggingMapper;
    private ApplicationProperties applicationProperties;

    @Override
    @Transactional
    public void insertInBulk(CreateQuizParam createQuizParam) throws IOException {
        Quiz quiz = new Quiz();
        quiz.setCreateUserid(Constants.GUEST_ID);

        // TODO:setterで自動変換したい
        if (createQuizParam.getThumbnail() != null) {
            String fileName = FileUtil.saveImageByBase64(applicationProperties.getImageThumbnailPath(),
                    createQuizParam.getThumbnail());
            quiz.setThumbnail(fileName);
        }
        quiz.setTitle(createQuizParam.getTitle());
        quiz.setDescription(createQuizParam.getDescription());
        quiz.setPublish(true);
        // quizmapper.insertが自動採番されたidをとってくれる
        // because useGeneratedKeys="true" on mapper
        quizMapper.insert(quiz);
        if (createQuizParam.getTags() != null && createQuizParam.getTags().size() != 0) {
            List<Tag> tags = insertAndUpdateTags(createQuizParam.getTags());
            insertQuizTagging(quiz.getId(), tags);
        }
        // パラメータが持つquestionをinsert
        insertQuestions(quiz.getId(), createQuizParam.getQuestions());

    }

    private List<Tag> insertAndUpdateTags(List<Tag> tagList) {
        List<Tag> retList = new ArrayList<>();
        for (Tag tag : tagList) {
            Tag already = tagMapper.selectByTagContent(tag.getTag());
            if (already == null) {
                tagMapper.insertSelective(tag);
                retList.add(tag);
            } else {
                retList.add(already);
            }
        }
        tagMapper.updateNum(retList.stream().map(t -> t.getId()).collect(Collectors.toList()));
        return retList;
    }

    private void insertQuizTagging(Long quizId, List<Tag> tags) {
        List<QuizTagging> quizTaggings = new ArrayList<>();
        for (Tag tag : tags) {
            QuizTagging quizTagging = new QuizTagging();
            quizTagging.setQuizId(quizId);
            quizTagging.setTagId(tag.getId());
            quizTaggings.add(quizTagging);
        }
        quizTaggingMapper.insertList(quizTaggings);

    }

    private void insertQuestions(long quizId, List<CreateQuestionParam> params) throws IOException {
        for (int i = 0; i < params.size(); i++) {
            CreateQuestionParam questionParam = params.get(i);
            Question question = new Question();
            question.setQuizId(quizId);
            question.setNum(i + 1);
            question.setContent(questionParam.getContent());
            question.setChoicetype(questionParam.getChoicetype().toString());
            question.setCreateUserid(questionParam.getCreateUserId());
            questionMapper.insert(question);
            insertChoice(questionParam.getChoicetype(), quizId, question.getId(), questionParam.getChoices());
        }

    }

    private void insertChoice(ChoiceTypeEnum choiceType, Long quizId, Long questionId, List<CreateChoiceParam> params)
            throws IOException {
        switch (choiceType) {
            case single:
                insertSingleChoice(quizId, questionId, params);
                break;
            case image:
                insertImageChoice(quizId, questionId, params);
                break;
        }
    }

    private void insertSingleChoice(long quizId, long questionId, List<CreateChoiceParam> params) {
        for (int i = 0; i < params.size(); i++) {
            CreateChoiceParam choiceParam = params.get(i);

            long choiceId = Long.valueOf(String.valueOf(i) + String.valueOf(questionId));

            Choice choice = new Choice();

            choice.setId(choiceId);
            choice.setQuizId(quizId);
            choice.setQuestionId(questionId);
            choice.setContent(choiceParam.getContent());
            choice.setCorrectFlg(choiceParam.getCorrectFlg());
            choice.setSelectionNo(i);
            choice.setCreateUserid(choiceParam.getCreateuserId());

            choiceMapper.insert(choice);
        }

    }

    private void insertImageChoice(long quizId, long questionId, List<CreateChoiceParam> params) throws IOException {
        for (int i = 0; i < params.size(); i++) {
            CreateChoiceParam questionParam = params.get(i);
            long choiceId = Long.valueOf(String.valueOf(i) + String.valueOf(questionId));
            Choice choice = new Choice();
            choice.setId(choiceId);
            choice.setQuizId(quizId);
            choice.setQuestionId(questionId);
            choice.setSelectionNo(i);
            choice.setCreateUserid(questionParam.getCreateuserId());
            // TODO:setterで自動変換したい
            String fileName = FileUtil.saveImageByBase64(applicationProperties.getImageChoicePath(),
                    questionParam.getContent());
            choice.setContent(fileName);

            choice.setCorrectFlg(questionParam.getCorrectFlg());

            choiceMapper.insert(choice);
        }
    }
}
