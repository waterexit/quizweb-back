package quizweb.domain.service;

import java.io.IOException;
import java.util.List;

import quizweb.common.enums.ChoiceTypeEnum;
import quizweb.domain.repository.entity.Choice;
import quizweb.object.CreateChoiceParam;

public interface CreateChoiceService {
    public Choice createChoice(long quizId, long questionId, int selectionNo, boolean correctFlg);

    public void insertChoices(ChoiceTypeEnum choiceType, List<CreateChoiceParam> createChoiceParam) throws IOException;
}
