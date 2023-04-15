package quizweb.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import quizweb.app.authentic.entity.TwitterUser;
import quizweb.common.exception.AuthFailException;
import quizweb.domain.repository.entity.Choice;
import quizweb.domain.repository.mapper.base.ChoiceMapper;
import quizweb.domain.service.EditChoiceService;

@Service
public class EditChoiceServiceImpl implements EditChoiceService {

    @Autowired
    public EditChoiceServiceImpl(ChoiceMapper choiceMapper) {
        this.choiceMapper = choiceMapper;
    }

    private ChoiceMapper choiceMapper;

    @Override
    public void editChoice(Choice choice) throws AuthFailException {
        authUserInfo(choice.getId());
        choiceMapper.updateByPrimaryKeySelective(choice);
    }

    private void authUserInfo(long choiceId) throws AuthFailException {
        TwitterUser twitterUser = (TwitterUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Choice choice = choiceMapper.selectByPrimaryKey(choiceId);
        if (!choice.getCreateUserid().equals(twitterUser.getUserId())) {
            throw new AuthFailException();
        }
    }
}
