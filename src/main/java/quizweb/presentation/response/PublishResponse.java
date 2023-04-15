package quizweb.presentation.response;

public class PublishResponse {
    public PublishResponse(long quizId, String title) {
        this.quizId = quizId;
        this.title = title;
    }

    private long quizId;
    private String title;

    public long getQuizId() {
        return quizId;
    }

    public String getTitle() {
        return title;
    }

}
