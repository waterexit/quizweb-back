package quizweb.presentation.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import quizweb.domain.repository.entity.Tag;
import quizweb.domain.service.AddTagService;
import quizweb.domain.service.GetTagService;
import quizweb.presentation.request.AddTagRequest;
import quizweb.presentation.request.GetTagListRequest;
import quizweb.presentation.response.GetTagListResponse;

@RestController
public class TagController {

    @Autowired
    public TagController(GetTagService getTagService, AddTagService addTagService) {
        this.getTagService = getTagService;
        this.addTagService = addTagService;
    }

    private GetTagService getTagService;
    private AddTagService addTagService;

    @GetMapping("/taglist/prefix")
    public GetTagListResponse getTag(GetTagListRequest req) {
        List<Tag> tagList = getTagService.getTagByPrefix(req.getPrefix());
        GetTagListResponse res = new GetTagListResponse(tagList);
        return res;
    }

    @PostMapping("/tag/add")
    public void addTag(AddTagRequest req) {
        addTagService.addTag(req.getQuizId(), req.getTag());
    }
}
