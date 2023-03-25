package quizweb.presentation.request;

import com.fasterxml.jackson.annotation.JsonCreator;

import quizweb.domain.service.valueobject.SearchCondition;

public class GetQuizRequest {

    @JsonCreator
    public GetQuizRequest(SearchCondition searchConditions, int page, int fetchSize) {
        this.searchConditions = searchConditions;
        this.page = page;
        this.fetchSize = fetchSize;
    }

    private SearchCondition searchConditions;

    private int page;

    private int fetchSize;

    public SearchCondition getSearchCondition() {
        return searchConditions;
    }

    public int getPage() {
        return page;
    }

    public int getFetchSize() {
        return fetchSize;
    }
}
