package quizweb.domain.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import quizweb.domain.repository.entity.Quiz;
import quizweb.domain.repository.entity.TaggingInfo;

@Mapper
public interface TaggingInfoMapper {
    List<TaggingInfo> getTaggingInfoByQuizList(List<Quiz> quizList);

    List<TaggingInfo> getTaggingInfoByQuizId(@Param("id") long quizId);
}
