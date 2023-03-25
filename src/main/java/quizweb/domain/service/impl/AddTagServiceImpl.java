package quizweb.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quizweb.domain.repository.entity.QuizTagging;
import quizweb.domain.repository.entity.Tag;
import quizweb.domain.repository.mapper.QuizTaggingMapper;
import quizweb.domain.repository.mapper.TagMapper;
import quizweb.domain.service.AddTagService;

@Service
public class AddTagServiceImpl implements AddTagService {
    @Autowired
    public AddTagServiceImpl(TagMapper tagMapper, QuizTaggingMapper quizTaggingMapper) {
        this.tagMapper = tagMapper;
        this.quizTaggingMapper = quizTaggingMapper;
    }

    private TagMapper tagMapper;
    private QuizTaggingMapper quizTaggingMapper;

    @Override
    public void addTag(long quizId, String tagContent) {
        Tag tag = selectOrInsertTag(tagContent);
        QuizTagging quizTagging = new QuizTagging();
        quizTagging.setQuizId(quizId);
        quizTagging.setTagId(tag.getId());
        quizTaggingMapper.insert(quizTagging);
    }

    private Tag selectOrInsertTag(String tagContent) {
        Tag tag = tagMapper.selectByContent(tagContent);
        if (tag != null) {
            tag = new Tag();
            tag.setNum(0L);
            tag.setTag(tagContent);
            tagMapper.insert(tag);
        }
        return tag;
    }

}
