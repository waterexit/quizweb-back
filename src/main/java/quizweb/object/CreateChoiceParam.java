package quizweb.object;

public class CreateChoiceParam {
    private long questionId;
    private String content;
    private boolean correctFlg;
    private long createuserId;

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setCorrectFlg(boolean correctFlg) {
        this.correctFlg = correctFlg;
    }

    public boolean getCorrectFlg() {
        return correctFlg;
    }

    public long getCreateuserId() {
        return createuserId;
    }

    public void setCreateuserId(long createuserId) {
        this.createuserId = createuserId;
    }

}
