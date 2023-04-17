package quizweb.object;

public class CreateChoiceParam {
    private long id;
    private int selectionNo;
    private String content;
    private boolean correctFlg;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSelectionNo() {
        return selectionNo;
    }

    public void setSelectionNo(int selectionNo) {
        this.selectionNo = selectionNo;
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
}
