package quizweb.presentation.restcontroller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import quizweb.common.exception.AuthFailException;
import quizweb.domain.repository.entity.Quiz;
import quizweb.domain.service.CreateQuizService;
import quizweb.domain.service.EditQuizService;
import quizweb.domain.service.GetQuizService;
import quizweb.domain.service.valueobject.QuizInfo;
import quizweb.presentation.request.EditQuizRequest;
import quizweb.presentation.request.GetQuizRequest;
import quizweb.presentation.request.PublishRequest;
import quizweb.presentation.response.GetQuizResponse;
import quizweb.presentation.response.NewQuizResponse;
import quizweb.presentation.response.PublishResponse;

@RestController
public class QuizController {
    @Autowired
    public QuizController(GetQuizService getQuizService, CreateQuizService createQuizService,
            EditQuizService editQuizService) {
        this.getQuizService = getQuizService;
        this.createQuizService = createQuizService;
        this.editQuizService = editQuizService;
    }

    GetQuizService getQuizService;
    CreateQuizService createQuizService;
    EditQuizService editQuizService;

    @PostMapping("/quiz/getlist")
    // RestControllerの場合、responseBodyは省略可能
    // @ResponseBody
    public GetQuizResponse getQuizList(@RequestBody GetQuizRequest request) {
        int count = getQuizService.getCountSearchedQuiz(request);
        List<QuizInfo> quizesInfo = getQuizService.getQuizList(request);
        GetQuizResponse res = new GetQuizResponse(quizesInfo, count);
        return res;
    }

    @PostMapping("/quiz/new")
    public NewQuizResponse newQuiz() {
        Quiz tmp = createQuizService.newQuiz();
        NewQuizResponse res = new NewQuizResponse(tmp.getId(), tmp.getCreateUserid());
        return res;
    }

    @PutMapping("/quiz/edit/save")
    public void saveQuiz(@RequestBody EditQuizRequest request) throws IOException {
        try {
            editQuizService.editQuiz(request.getQuiz());
        } catch (AuthFailException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/quiz/publish")
    public PublishResponse publsh(@RequestBody PublishRequest req) {
        try {
            String title = editQuizService.publish(req.getQuizId());
            PublishResponse res = new PublishResponse(req.getQuizId(), title);
            return res;
        } catch (AuthFailException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

}
