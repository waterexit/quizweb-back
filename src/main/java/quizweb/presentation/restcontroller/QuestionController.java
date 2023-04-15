package quizweb.presentation.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import quizweb.common.exception.AuthFailException;
import quizweb.domain.repository.entity.Question;
import quizweb.domain.service.CreateQuestionService;
import quizweb.domain.service.EditQuestionService;
import quizweb.presentation.request.CreateQuestionRequest;
import quizweb.presentation.request.EditQuestionRequest;
import quizweb.presentation.response.CreateQuesitonResponse;

@RestController
public class QuestionController {

    @Autowired
    public QuestionController(CreateQuestionService createQuestionService, EditQuestionService editQuestionService) {
        this.createQuestionService = createQuestionService;
        this.editQuestionService = editQuestionService;
    }

    private CreateQuestionService createQuestionService;

    private EditQuestionService editQuestionService;

    @PostMapping("/question/new")
    public CreateQuesitonResponse newQuestion(@RequestBody CreateQuestionRequest req) {
        Question tmp = createQuestionService.createQuestion(req.getQuizId(), req.getNum(), req.getChoiceType());
        CreateQuesitonResponse res = new CreateQuesitonResponse(tmp.getId(), tmp.getCreateUserid());
        return res;
    }

    @PutMapping("/question/edit/save")
    public void saveQuiz(@RequestBody EditQuestionRequest request) {
        try {
            editQuestionService.editQuestion(request.getQuestion());
        } catch (AuthFailException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
