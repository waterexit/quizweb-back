package quizweb.presentation.request;

import com.fasterxml.jackson.annotation.JsonCreator;

import quizweb.common.enums.ChoiceTypeEnum;

public class CreateQuestionRequest {
    @JsonCreator
    public CreateQuestionRequest(long quizId, int num, String choicetype) {
        this.quizId = quizId;
        this.num = num;
        this.choicetype = ChoiceTypeEnum.valueOf(choicetype);
    }

    private long quizId;

    private int num;

    private ChoiceTypeEnum choicetype;

    public long getQuizId() {
        return quizId;
    }

    public int getNum() {
        return num;
    }

    public ChoiceTypeEnum getChoiceType() {
        return choicetype;
    }
}
