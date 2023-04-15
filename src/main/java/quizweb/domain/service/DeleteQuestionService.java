package quizweb.domain.service;

public interface DeleteQuestionService {
    public void deleteQuestionById(long questionId);

    public void deleteQuestionByQuizId(long quizId);

    public void deleteStagingQuestionById(long questionId);

    public void deleteStagingQuestionByQuizId(long quizId);

}
