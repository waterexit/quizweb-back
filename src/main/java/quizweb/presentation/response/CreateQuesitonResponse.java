package quizweb.presentation.response;

public class CreateQuesitonResponse {

    public CreateQuesitonResponse(long questionId, long createUserId) {
        this.questionId = questionId;
        this.createUserId = createUserId;
    }

    private long questionId;

    public long getQuestionId() {
        return questionId;
    }

    private long createUserId;

    public long getCreateUserId() {
        return createUserId;
    }

}
