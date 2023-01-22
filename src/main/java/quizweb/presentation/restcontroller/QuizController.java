package quizweb.presentation.restcontroller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import quizweb.domain.service.CreateQuizService;
import quizweb.domain.service.EditQuizService;
import quizweb.domain.service.GetQuizService;
import quizweb.domain.service.valueobject.QuizesInfo;
import quizweb.presentation.request.CreateQuizRequest;
import quizweb.presentation.request.GetQuizRequest;

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

    @PostMapping("/quiz/get")
    @ResponseBody
    public QuizesInfo getQuiz(@RequestBody GetQuizRequest request) {

        QuizesInfo quizesInfo = getQuizService.getQuizList(request);
        return quizesInfo;
    }

    @GetMapping("/quiz/create")
    public long createQuiz() {
        Long quizId = createQuizService.createQuizSkeleton();

        return quizId;
    }

    @PostMapping("/quiz/edit")
    public boolean createQuestion(@RequestBody CreateQuizRequest request) {
        try {
            editQuizService.editQuiz(request.getCreateQuizParam());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}