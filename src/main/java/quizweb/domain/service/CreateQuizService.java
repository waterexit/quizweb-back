package quizweb.domain.service;

import java.io.IOException;

import quizweb.object.CreateQuizParam;

public interface CreateQuizService {
    
    public void createQuiz(CreateQuizParam createQuizParam) throws IOException;

}
