package quizweb.domain.service;

import quizweb.domain.service.valueobject.QuizesInfo;
import quizweb.presentation.request.GetQuizRequest;

public interface GetQuizService {

    public QuizesInfo getQuizList(GetQuizRequest request);

}
