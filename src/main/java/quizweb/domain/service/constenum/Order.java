package quizweb.domain.service.constenum;

public enum Order {
    newOrder("quiz.id DESC"),
    oldOrder("quiz.id"),
    viewOrder("answerer_num DESC");

    private String value;

    private Order(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    
    
}
