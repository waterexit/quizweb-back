package quizweb.domain.service;

import quizweb.domain.repository.entity.Quiz;

public interface CreateQuizService {
    // public void createQuiz(CreateQuizParam createQuizParam) throws IOException;
    public Quiz newQuiz();
}
