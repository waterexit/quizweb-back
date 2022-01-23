package quizweb.domain.service.valueobject;

import java.util.List;

import quizweb.domain.repository.entity.Tag;
import quizweb.domain.service.constenum.Order;

public class SearchCondition {
    public SearchCondition(String title, Order order, List<Tag> tags, Long userId) {
        this.title = title;
        this.order = order;
        this.tags = tags;
        this.userId = userId;
    }

    private String title;

    private Order order;

    private List<Tag> tags;

    private long userId;

    public String getTitle() {
        return title;
    }

    public Order getOrder() {
        return order;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public long getUserId() {
        return userId;
    }

}
