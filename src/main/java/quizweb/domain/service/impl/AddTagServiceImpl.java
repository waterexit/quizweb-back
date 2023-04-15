package quizweb.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import quizweb.app.authentic.entity.TwitterUser;
import quizweb.common.exception.AuthFailException;
import quizweb.domain.repository.entity.Quiz;
import quizweb.domain.repository.entity.QuizTagging;
import quizweb.domain.repository.entity.Tag;
import quizweb.domain.repository.mapper.QuizTaggingMapper;
import quizweb.domain.repository.mapper.TagMapper;
import quizweb.domain.repository.mapper.base.QuizMapper;
import quizweb.domain.service.AddTagService;

@Service
public class AddTagServiceImpl implements AddTagService {
    @Autowired
    public AddTagServiceImpl(TagMapper tagMapper, QuizTaggingMapper quizTaggingMapper, QuizMapper quizMapper) {
        this.tagMapper = tagMapper;
        this.quizTaggingMapper = quizTaggingMapper;
        this.quizMapper = quizMapper;
    }

    private TagMapper tagMapper;
    private QuizTaggingMapper quizTaggingMapper;
    private QuizMapper quizMapper;

    @Override
    public void addTag(long quizId, String tagContent) throws AuthFailException {
        authUserInfo(quizId);
        Tag tag = selectOrInsertTag(tagContent);
        QuizTagging quizTagging = new QuizTagging();
        quizTagging.setQuizId(quizId);
        quizTagging.setTagId(tag.getId());
        quizTaggingMapper.insert(quizTagging);
    }

    private Tag selectOrInsertTag(String tagContent) {
        Tag tag = tagMapper.selectByTagContent(tagContent);
        if (tag != null) {
            tag = new Tag();
            tag.setNum(0L);
            tag.setTag(tagContent);
            tagMapper.insert(tag);
        }
        return tag;
    }

    private void authUserInfo(long quizId) throws AuthFailException {
        TwitterUser twitterUser = (TwitterUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Quiz quiz = quizMapper.selectByPrimaryKey(quizId);
        if (!quiz.getCreateUserid().equals(twitterUser.getUserId())) {
            throw new AuthFailException();
        }
    }
}
