package quizweb.presentation.restcontroller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import quizweb.domain.repository.entity.QuizDetail;
import quizweb.domain.service.EditQuizService;
import quizweb.domain.service.GetQuizDetailService;
import quizweb.domain.service.InsertInBulkService;
import quizweb.object.CreateQuizParam;
import quizweb.presentation.request.InsertInBulkRequest;
import quizweb.presentation.response.InsertInBulkResponse;

@RestController
public class AllQuizInfoController {
    @Autowired
    public AllQuizInfoController(GetQuizDetailService getQuizDetailService, InsertInBulkService insertInBulkService,
            EditQuizService editQuizService) {
        this.getQuizDetailService = getQuizDetailService;
        this.insertInBulkService = insertInBulkService;
        this.editQuizService = editQuizService;
    }

    private GetQuizDetailService getQuizDetailService;

    private InsertInBulkService insertInBulkService;

    private EditQuizService editQuizService;

    @GetMapping("/quizDetail")
    @ResponseBody
    public QuizDetail getQuestionDetail(@RequestParam("quizId") String id) {

        QuizDetail quizDetail = getQuizDetailService.getQuizDetail(id);
        return quizDetail;
    }

    @PostMapping("/insert/inbulk")
    public InsertInBulkResponse insertInBluk(@RequestBody InsertInBulkRequest req) throws IOException {
        insertInBulkService.insertInBulk(req.getCreateQuizParam());
        InsertInBulkResponse res = new InsertInBulkResponse(req.getCreateQuizParam().getId(), req.getCreateQuizParam().getTitle());
        return res;
    }

    @GetMapping("/test/test")
    @ResponseBody
    public CreateQuizParam getEditParam(@RequestParam("quizId") long id) {
        try {
            return editQuizService.getDataForEdit(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

    }

}
