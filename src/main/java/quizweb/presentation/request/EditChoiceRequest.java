package quizweb.presentation.request;

import com.fasterxml.jackson.annotation.JsonCreator;

import quizweb.domain.repository.entity.Choice;

public class EditChoiceRequest {
    @JsonCreator
    public EditChoiceRequest(Choice choice) {
        this.choice = choice;
    }

    private Choice choice;

    public Choice getChoice() {
        return choice;
    }
}
