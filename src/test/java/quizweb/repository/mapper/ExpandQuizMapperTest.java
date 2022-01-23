package quizweb.repository.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import quizweb.domain.repository.mapper.ExpandQuizMapper;
import quizweb.domain.service.constenum.Order;
import quizweb.domain.service.valueobject.SearchCondition;

@SpringBootTest
public class ExpandQuizMapperTest {


    @Autowired
    ExpandQuizMapper target;

    // @Autowired
    // ChoiceMapper target2;

    @Test
    public void test1(){
        SearchCondition searchCondition = new SearchCondition("titl", Order.newOrder, null, 1l);  
        
        target.count(searchCondition);
    }
}
