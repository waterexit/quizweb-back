package quizweb.domain.repository.mapper;

import org.apache.ibatis.annotations.Mapper;

import quizweb.domain.repository.entity.Choice;
import quizweb.domain.repository.mapper.base.ChoiceMapper;

@Mapper
public interface ExpandChoiceMapper extends ChoiceMapper{
    Choice selectCorrectChoice(Long QuestionId);

    int deleteByQuizId(Long quizId);
}
