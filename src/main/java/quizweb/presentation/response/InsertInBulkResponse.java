package quizweb.presentation.response;

public class InsertInBulkResponse {
    public InsertInBulkResponse(long quizId, String title) {
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
