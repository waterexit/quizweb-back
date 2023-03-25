package quizweb.presentation.response;

public class CreateChoiceResponse {
    public CreateChoiceResponse(long questionId, long createUserId) {
        this.choiceId = questionId;
        this.createUserId = createUserId;
    }

    private long choiceId;

    public long getChoiceId() {
        return choiceId;
    }

    private long createUserId;

    public long getCreateUserId() {
        return createUserId;
    }

}
