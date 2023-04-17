package quizweb.object;

import java.util.List;

import quizweb.common.enums.ChoiceTypeEnum;

public class CreateQuestionParam {
    private long id;
    private int num;
    private String content;
    private String comment;
    private ChoiceTypeEnum choicetype;
    private List<CreateChoiceParam> choices;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

}
