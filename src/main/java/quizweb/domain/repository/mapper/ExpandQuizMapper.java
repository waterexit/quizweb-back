package quizweb.domain.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import quizweb.domain.repository.entity.Quiz;
import quizweb.domain.service.valueobject.SearchCondition;

@Mapper
public interface ExpandQuizMapper {
    int count(@Param("searchCondition") SearchCondition searchCondition);

    List<Quiz> fetchQuizes(@Param("searchCondition") SearchCondition searchCondition,
    @Param("limit") int limit, @Param("offset") int offset);

}
