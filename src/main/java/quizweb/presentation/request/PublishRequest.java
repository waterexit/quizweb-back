package quizweb.presentation.request;

import com.fasterxml.jackson.annotation.JsonCreator;

public class PublishRequest {
    @JsonCreator
    public PublishRequest(long quizId) {
        this.quizId = quizId;
    }

    private long quizId;

    public long getQuizId() {
        return quizId;
    }
}
