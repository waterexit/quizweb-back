package quizweb.domain.repository.entity;

import java.util.List;

public class QuizDetail extends Quiz {

    private List<QuestionDetail> questions;

    private List<Tag> tags;

    public List<QuestionDetail> getQuestions() {
        return questions;
    }

    public List<Tag> geTags() {
        return tags;
    }

}
