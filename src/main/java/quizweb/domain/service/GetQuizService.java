package quizweb.domain.service;

import java.util.List;

import quizweb.domain.service.valueobject.QuizInfo;
import quizweb.presentation.request.GetQuizRequest;

public interface GetQuizService {
    public int getCountSearchedQuiz(GetQuizRequest request);

    public List<QuizInfo> getQuizList(GetQuizRequest request);

}
