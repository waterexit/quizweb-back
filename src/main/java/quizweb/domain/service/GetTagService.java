package quizweb.domain.service;

import java.util.List;

import quizweb.domain.repository.entity.Tag;

public interface GetTagService {
   public List<Tag> getTagByPrefix(String prefix); 
}
