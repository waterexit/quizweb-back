package quizweb.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import quizweb.app.authentic.entity.TwitterUser;
import quizweb.common.constant.Constants;
import quizweb.common.enums.ChoiceTypeEnum;
import quizweb.domain.repository.entity.Question;
import quizweb.domain.repository.entity.StagingQuestion;
import quizweb.domain.repository.mapper.base.QuestionMapper;
import quizweb.domain.repository.mapper.base.StagingQuestionMapper;
import quizweb.domain.service.CreateQuestionService;
import quizweb.object.CreateQuestionParam;

@Service
public class CreateQuestionServiceImpl implements CreateQuestionService {

    @Autowired
    public CreateQuestionServiceImpl(QuestionMapper questionMapper,StagingQuestionMapper stagingQuestionMapper) {
        this.questionMapper = questionMapper;
        this.stagingQuestionMapper = stagingQuestionMapper;
    }

    private QuestionMapper questionMapper;
    private StagingQuestionMapper stagingQuestionMapper;

    @Override
    public Question createQuestion(long quizId, int num, ChoiceTypeEnum choiceType) {
        Question question = new Question();
        question.setQuizId(quizId);
        question.setNum(num);
        question.setChoicetype(choiceType.name());
        Object authInfo = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (authInfo instanceof TwitterUser) {
            question.setCreateUserid(((TwitterUser) authInfo).getUserId());
            questionMapper.insert(question);
            StagingQuestion stagingQuestion = new StagingQuestion();
            stagingQuestion.setQuestionId(question.getId());
            stagingQuestion.setNum(num);
            stagingQuestion.setChoicetype(choiceType.name());
            stagingQuestionMapper.insert(stagingQuestion);
        } else {
            question.setCreateUserid(Constants.GUEST_ID);
            questionMapper.insert(question);
            // HACK: AUTOINCREMENTの値を取るためにinsertの後にdelete
            questionMapper.deleteByPrimaryKey(question.getId());
        }

        return question;
    }

    @Override
    public void insertQuestions(List<CreateQuestionParam> params) {
        for (int i = 0; i < params.size(); i++) {
            CreateQuestionParam questionParam = params.get(i);
            Question question = new Question();
            question.setQuizId(questionParam.getQuizId());
            question.setNum(i + 1);
            question.setContent(questionParam.getContent());
            question.setChoicetype(questionParam.getChoicetype().toString());
            questionMapper.insert(question);
        }
    }

}
