package quizweb.domain.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quizweb.domain.repository.entity.Tag;
import quizweb.domain.repository.mapper.TagMapper;
import quizweb.domain.service.InsertTagService;

@Service
public class InsertTagServiceImpl implements InsertTagService {
    @Autowired
    public InsertTagServiceImpl(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    private TagMapper tagMapper;

    @Override
    public void insertTag(List<String> contents) {
        TagLists taglists = separateTagList(contents);
        
        tagMapper.insertList(taglists.insertList);
        tagMapper.updateNum(taglists.updateList);

    }

    class TagLists {
        List<Tag> insertList;
        List<Tag> updateList;

        private TagLists(List<Tag> insertList, List<Tag> updateList) {
            this.insertList = insertList;
            this.updateList = updateList;
        }

    }

    TagLists separateTagList(List<String> contents) {
        List<Tag> updateList = tagMapper.selectByTagContents(contents);

        List<Tag> insertList = contents.stream().filter(c -> updateList.stream().noneMatch(t -> t.getTag().equals(c)))
                .map(c -> {
                    Tag t = new Tag();
                    t.setTag(c);
                    return t;
                }).collect(Collectors.toList());

        return new TagLists(insertList, updateList);
    }
}
