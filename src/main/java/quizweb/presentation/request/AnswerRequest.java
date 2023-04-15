package quizweb.presentation.request;

import com.fasterxml.jackson.annotation.JsonCreator;

public class AnswerRequest {
    @JsonCreator
    public AnswerRequest(long questionId, long selectionNo) {
        this.questionId = questionId;
        this.selectionNo = selectionNo;
    }

    private long questionId;

    private long selectionNo;

    public long getQuestionId() {
        return questionId;
    }

    public long getSelectionNo() {
        return selectionNo;
    }

}
