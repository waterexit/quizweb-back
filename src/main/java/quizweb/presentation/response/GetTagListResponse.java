package quizweb.presentation.response;

import java.util.List;

import quizweb.domain.repository.entity.Tag;

public class GetTagListResponse {
    public GetTagListResponse(List<Tag> tagList) {
        this.tagList = tagList;
    }

    private List<Tag> tagList;

    public List<Tag> getTagList() {
        return tagList;
    }
}
