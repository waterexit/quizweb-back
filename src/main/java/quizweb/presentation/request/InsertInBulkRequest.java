package quizweb.presentation.request;

import quizweb.object.CreateQuizParam;

public class InsertInBulkRequest {
    public InsertInBulkRequest(CreateQuizParam createQuizParam) {
        this.createQuizParam = createQuizParam;
    }

    private CreateQuizParam createQuizParam;

    public CreateQuizParam getCreateQuizParam() {
        return createQuizParam;
    }
}
