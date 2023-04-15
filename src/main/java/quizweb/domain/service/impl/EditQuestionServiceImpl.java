package quizweb.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import quizweb.app.authentic.entity.TwitterUser;
import quizweb.common.exception.AuthFailException;
import quizweb.domain.repository.entity.Question;
import quizweb.domain.repository.mapper.base.QuestionMapper;
import quizweb.domain.service.EditQuestionService;

@Service
public class EditQuestionServiceImpl implements EditQuestionService {

    @Autowired
    public EditQuestionServiceImpl(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    private QuestionMapper questionMapper;

    @Override
    public void editQuestion(Question question) throws AuthFailException {
        authUserInfo(question.getId());
        questionMapper.updateByPrimaryKeySelective(question);
    }

    private void authUserInfo(long questionId) throws AuthFailException {
        TwitterUser twitterUser = (TwitterUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Question question = questionMapper.selectByPrimaryKey(questionId);
        if (!question.getCreateUserid().equals(twitterUser.getUserId())) {
            throw new AuthFailException();
        }
    }

}
