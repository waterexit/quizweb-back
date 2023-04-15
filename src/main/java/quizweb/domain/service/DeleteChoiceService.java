package quizweb.domain.service;

public interface DeleteChoiceService {
    public void deleteChoiceById(long choiceId);
    
    public void deleteChoiceByQuestionId(long questionId);

    public void deleteChoiceByQuizId(long quizId);

    public void deleteStagingChoiceById(long choiceId);

    public void deleteStagingChiceByQuestionId(long quizId);

    public void deleteStagingChoiceByQuizId(long quizId);

}
