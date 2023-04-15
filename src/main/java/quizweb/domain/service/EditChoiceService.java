package quizweb.domain.service;

import quizweb.common.exception.AuthFailException;
import quizweb.domain.repository.entity.Choice;

public interface EditChoiceService {
    public void editChoice(Choice choice) throws AuthFailException;
}
