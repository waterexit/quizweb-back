package quizweb.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import quizweb.app.authentic.entity.TwitterUser;
import quizweb.common.constant.Constants;
import quizweb.domain.repository.entity.Quiz;
import quizweb.domain.repository.entity.StagingQuiz;
import quizweb.domain.repository.mapper.base.QuizMapper;
import quizweb.domain.repository.mapper.base.StagingQuizMapper;
import quizweb.domain.service.CreateQuizService;

@Service
public class CreateQuizServiceImpl implements CreateQuizService {
    @Autowired
    public CreateQuizServiceImpl(QuizMapper quizMapper, StagingQuizMapper stagingQuizMapper) {
        this.quizMapper = quizMapper;
        this.stagingQuizMapper = stagingQuizMapper;
    }

    private QuizMapper quizMapper;
    private StagingQuizMapper stagingQuizMapper;

    @Override
    public Quiz newQuiz() {
        Quiz quiz = new Quiz();
        quiz.setPublish(false);
        Object authInfo = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (authInfo instanceof TwitterUser) {
            quiz.setCreateUserid(((TwitterUser) authInfo).getUserId());
            quizMapper.insert(quiz);
            StagingQuiz stagingQuiz = new StagingQuiz();
            stagingQuiz.setQuizId(quiz.getId());
            stagingQuizMapper.insert(stagingQuiz);
        } else {
            quiz.setCreateUserid(Constants.GUEST_ID);
            // HACK: AUTOINCREMENTの値を取るためにinsertの後にdelete
            quizMapper.insert(quiz);
            quizMapper.deleteByPrimaryKey(quiz.getId());
        }

        return quiz;
    }

}
