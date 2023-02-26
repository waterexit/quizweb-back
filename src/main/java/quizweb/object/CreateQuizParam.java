package quizweb.object;

import java.util.List;

import quizweb.domain.repository.entity.Tag;

public class CreateQuizParam {
    private long id;

    private String thumbnail;

    private String title;

    private String description;

    private List<Tag> tags;

    private List<CreateQuestionParam> questions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<CreateQuestionParam> getQuestions() {
        return questions;
    }

    public void setQuestions(List<CreateQuestionParam> questions) {
        this.questions = questions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
