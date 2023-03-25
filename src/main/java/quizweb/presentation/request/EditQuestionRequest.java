package quizweb.presentation.request;

import quizweb.domain.repository.entity.Question;

public class EditQuestionRequest {
    public EditQuestionRequest(Question question) {
        this.question = question;
    }

    private Question question;

    public Question getQuestion() {
        return question;
    }
}
