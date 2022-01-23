package quizweb.repository.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import quizweb.domain.repository.mapper.ExpandChoiceMapper;

@SpringBootTest
public class ExpandChoiceMapperTest {


    @Autowired
    ExpandChoiceMapper target;

    // @Autowired
    // ChoiceMapper target2;

    @Test
    public void test1(){
         target.selectCorrectChoice(1l);
    }
}
