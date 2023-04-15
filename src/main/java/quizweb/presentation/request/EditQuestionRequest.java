package quizweb.presentation.request;

import com.fasterxml.jackson.annotation.JsonCreator;

import quizweb.domain.repository.entity.Question;

public class EditQuestionRequest {
    @JsonCreator
    public EditQuestionRequest(Question question) {
        this.question = question;
    }

    private Question question;

    public Question getQuestion() {
        return question;
    }
}
