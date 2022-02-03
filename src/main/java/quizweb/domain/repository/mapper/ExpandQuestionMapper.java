package quizweb.domain.repository.mapper;

import org.apache.ibatis.annotations.Mapper;

import quizweb.domain.repository.mapper.base.QuestionMapper;

@Mapper
public interface ExpandQuestionMapper extends QuestionMapper{
    int deleteByQuizId(Long quizId);
}
