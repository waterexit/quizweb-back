package quizweb.presentation.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import quizweb.common.exception.AuthFailException;
import quizweb.domain.repository.entity.Choice;
import quizweb.domain.service.CreateChoiceService;
import quizweb.domain.service.EditChoiceService;
import quizweb.presentation.request.CreateChoiceRequest;
import quizweb.presentation.request.EditChoiceRequest;
import quizweb.presentation.response.CreateChoiceResponse;

@RestController
public class ChoiceControrller {

    @Autowired
    public ChoiceControrller(CreateChoiceService createChoiceService, EditChoiceService editChoiceService) {
        this.createChoiceService = createChoiceService;
        this.editChoiceService = editChoiceService;
    }

    private CreateChoiceService createChoiceService;
    private EditChoiceService editChoiceService;

    @PostMapping("/choice/new")
    public CreateChoiceResponse newChoice(@RequestBody CreateChoiceRequest req) {
        Choice tmp = createChoiceService.createChoice(req.getQuizId(), req.getQuestionId(), req.getSelectionNo(),
                req.getCorrectFlg());
        CreateChoiceResponse res = new CreateChoiceResponse(tmp.getId(), tmp.getCreateUserid());
        return res;
    }

    @PutMapping("/choice/edit/save")
    public void EditChoice(@RequestBody EditChoiceRequest req) {
        try {
            editChoiceService.editChoice(req.getChoice());
        } catch (AuthFailException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

}
