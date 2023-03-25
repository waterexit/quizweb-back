package quizweb.presentation.request;

import com.fasterxml.jackson.annotation.JsonCreator;

import quizweb.domain.repository.entity.Quiz;

public class EditQuizRequest {
    @JsonCreator
    public EditQuizRequest(Quiz quiz) {
        this.quiz = quiz;
    }

    private Quiz quiz;

    public Quiz getQuiz() {
        return quiz;
    }

}
