package quizweb.presentation.request;

import com.fasterxml.jackson.annotation.JsonCreator;

public class AddTagRequest {

    @JsonCreator
    public AddTagRequest(long quizId, String tag) {
        this.quizId = quizId;
        this.tag = tag;
    }

    private long quizId;

    private String tag;

    public long getQuizId() {
        return quizId;
    }

    public String getTag() {
        return tag;
    }
}
