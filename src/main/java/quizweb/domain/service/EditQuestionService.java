package quizweb.domain.service;

import quizweb.common.exception.AuthFailException;
import quizweb.domain.repository.entity.Question;

public interface EditQuestionService {
    public void editQuestion(Question question) throws AuthFailException;
}
