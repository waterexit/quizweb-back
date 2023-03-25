package quizweb.presentation.request;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CreateChoiceRequest {
    @JsonCreator
    public CreateChoiceRequest(long quizId, long questionId, int selectionNo, boolean correctFlg) {
        this.quizId = quizId;
        this.questionId = questionId;
        this.selectionNo = selectionNo;
        this.correctFlg = correctFlg;
    }

    private long quizId;

    private long questionId;

    private int selectionNo;

    private boolean correctFlg;

    public long getQuizId() {
        return quizId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public int getSelectionNo() {
        return selectionNo;
    }

    public boolean getCorrectFlg() {
        return correctFlg;
    }

}
