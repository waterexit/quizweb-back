package quizweb.presentation.request;

import quizweb.object.CreateQuizParam;

public class CreateQuizRequest {
    private CreateQuizParam createQuizParam;

    public CreateQuizParam getCreateQuizParam() {
        return createQuizParam;
    }

    public void setCreateQuizParam(CreateQuizParam createQuizParam) {
        this.createQuizParam = createQuizParam;
    }
}
