package quizweb.domain.service;

import java.io.IOException;

import quizweb.object.CreateQuizParam;

public interface EditQuizService {
    public CreateQuizParam getEditParam(long id);

    public void editQuiz(CreateQuizParam createQuizParam) throws IOException;

    public void publish(Long quizId);
}
