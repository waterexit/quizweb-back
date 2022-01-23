package quizweb.domain.repository.mapper;

import org.apache.ibatis.annotations.Mapper;

import quizweb.domain.repository.entity.Choice;

@Mapper
public interface ExpandChoiceMapper {
    Choice selectCorrectChoice(Long QuestionId);
}
