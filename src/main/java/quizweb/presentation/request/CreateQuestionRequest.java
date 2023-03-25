package quizweb.presentation.request;

import com.fasterxml.jackson.annotation.JsonCreator;

import quizweb.common.enums.ChoiceTypeEnum;

public class CreateQuestionRequest {
    @JsonCreator
    public CreateQuestionRequest(long quizId, int num, String choiceType) {
        this.quizId = quizId;
        this.num = num;
        this.choiceType = ChoiceTypeEnum.valueOf(choiceType);
    }

    private long quizId;

    private int num;

    private ChoiceTypeEnum choiceType;

    public long getQuizId() {
        return quizId;
    }

    public int getNum() {
        return num;
    }

    public ChoiceTypeEnum getChoiceType() {
        return choiceType;
    }
}
