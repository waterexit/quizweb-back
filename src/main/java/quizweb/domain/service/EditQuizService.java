package quizweb.domain.service;

import java.io.IOException;

import quizweb.presentation.request.CreateQuizRequest.CreateQuizParam;

public interface EditQuizService {
    
    public void createQuiz(CreateQuizParam createQuizParam) throws IOException;

}
