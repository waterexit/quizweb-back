package quizweb.object;

import java.util.List;

import quizweb.common.enums.ChoiceTypeEnum;

public class CreateQuestionParam {
    private long quizId;

    private String content;

    private String comment;

    private ChoiceTypeEnum choicetype;

    private List<CreateChoiceParam> choices;

    private long createUserId;

    public long getQuizId() {
        return quizId;
    }

    public void setQuizId(long quizId) {
        this.quizId = quizId;
    }

    public String getContent() {
        return content;
    }

    public String getComment() {
        return comment;
    }

    public ChoiceTypeEnum getChoicetype() {
        return choicetype;
    }

    public List<CreateChoiceParam> getChoices() {
        return choices;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setChoiceType(String choicetype) {
        this.choicetype = ChoiceTypeEnum.valueOf(choicetype);
    }

    public void setChoices(List<CreateChoiceParam> choiceParams) {
        this.choices = choiceParams;
    }

    public long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(long createUserId) {
        this.createUserId = createUserId;
    }
}
