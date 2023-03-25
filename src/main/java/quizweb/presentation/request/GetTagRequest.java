package quizweb.presentation.request;

public class GetTagRequest {
    public GetTagRequest(String prefix) {
        this.prefix = prefix;
    }

    private String prefix;

    public String getPrefix() {
        return prefix;
    }
}
