package quizweb.object;

import java.util.List;

import quizweb.common.enums.ChoiceTypeEnum;

public class CreateQuestionParam {
    private String content;

    private String comment;

    private ChoiceTypeEnum choiceType;

    private List<CreateChoiceParam> choices;

    public String getContent() {
        return content;
    }

    public String getComment() {
        return comment;
    }

    public ChoiceTypeEnum getChoiceType() {
        return choiceType;
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

    public void setChoiceType(String choiceType) {
        this.choiceType = ChoiceTypeEnum.valueOf(choiceType);
    }

    public void setChoices(List<CreateChoiceParam> choiceParams) {
        this.choices = choiceParams;
    }
}
