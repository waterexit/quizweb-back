package quizweb.domain.service;

import java.util.List;

import quizweb.common.enums.ChoiceTypeEnum;
import quizweb.domain.repository.entity.Question;
import quizweb.object.CreateQuestionParam;

public interface CreateQuestionService {
    public Question createQuestion(long quizId, int num, ChoiceTypeEnum choiceTypeEnum);

    public void insertQuestions(List<CreateQuestionParam> params);
}
