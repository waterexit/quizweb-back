package quizweb.domain.service;

import java.io.IOException;

import quizweb.common.exception.AuthFailException;
import quizweb.domain.repository.entity.Quiz;
import quizweb.object.CreateQuizParam;

public interface EditQuizService {
    public CreateQuizParam getDataForEdit(long id) throws AuthFailException;

    public void editQuiz(Quiz quiz) throws AuthFailException,IOException;

    public String publish(Long quizId) throws AuthFailException;
}
