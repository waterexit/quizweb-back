package quizweb.presentation.response;

public class NewQuizResponse {
    public NewQuizResponse(long quizId, long createUserId) {
        this.quizId = quizId;
        this.createUserId = createUserId;
    }

    private long quizId;

    public long getQuizId() {
        return quizId;
    }

    private long createUserId;

    public long getCreateUserId() {
        return createUserId;
    }
}
