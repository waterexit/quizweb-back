package quizweb.presentation.request;

import com.fasterxml.jackson.annotation.JsonCreator;

public class GetTagListRequest {
    @JsonCreator
    public GetTagListRequest(String prefix) {
        this.prefix = prefix;
    }

    private String prefix;

    public String getPrefix() {
        return prefix;
    }
}
