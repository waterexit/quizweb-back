package quizweb.object;

public class CreateChoiceParam {
    private String content;

    private boolean correctFlg;

    public String getContent() {
        return content;
    }

    public boolean getCorrectFlg() {
        return correctFlg;
    }

    public void setCorrectFlg(boolean correctFlg) {
        this.correctFlg = correctFlg;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
