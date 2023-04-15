package quizweb.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import quizweb.domain.service.GetQuizService;

@SpringBootTest
public class GetQuestionServiceTest {
    @Autowired
    GetQuizService target;

    @Test
    public void searchByTitle() {

        // GetQuizRequest request = new GetQuizRequest();
        // SearchCondition searchConditions = new SearchCondition("進撃の巨人", Order.newOrder, null , null);

        // request.setSearchConditions(searchConditions);
        // request.setPage(1);
        // request.setFetchSize(10);

        // QuizesInfo result = target.getQuizList(request);

        // assertEquals("進撃の巨人クイズ", result.getQuizes().get(0).getTitle());

    }

    @Test
    public void searchByTag() {

        // GetQuizRequest request = new GetQuizRequest();
        // List<Tag> tags = new ArrayList<>();
        // Tag t1 = new Tag();
        // t1.setId(1l);
        // t1.setNum(2l);
        // t1.setTag("漫画");

        // tags.add(t1);
        // SearchCondition searchConditions = new SearchCondition("", Order.newOrder, tags, null);

        // request.setSearchConditions(searchConditions);
        // request.setPage(1);
        // request.setFetchSize(10);

        // QuizesInfo result = target.getQuizList(request);

        // assertEquals("進撃の巨人クイズ", result.getQuizes().get(0).getTitle());

    }
}
