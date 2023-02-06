package quizweb.domain.service;

import quizweb.domain.repository.entity.QuizDetail;

public interface GetQuizDetailService {
    public QuizDetail getQuizDetail(String id);
}
