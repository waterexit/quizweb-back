package quizweb.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quizweb.domain.repository.entity.Tag;
import quizweb.domain.repository.mapper.TagMapper;
import quizweb.domain.service.GetTagService;

@Service
public class GetTagServiceImpl implements GetTagService {
@Autowired
public GetTagServiceImpl(TagMapper tagMapper){
    this.tagMapper = tagMapper;
}
private TagMapper tagMapper;
    @Override
    public List<Tag> getTagByPrefix(String prefix) {
        List<Tag> tag = tagMapper.selectByPrefix(prefix);
        return tag;
    }
    
}
