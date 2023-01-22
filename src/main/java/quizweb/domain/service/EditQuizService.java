package quizweb.domain.service;

import java.io.IOException;

import quizweb.presentation.request.CreateQuizRequest.CreateQuizParam;

public interface EditQuizService {
    
    public void editQuiz(CreateQuizParam createQuizParam) throws IOException;

    public void publish(Long quizId);
    
}
