package quizweb.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import quizweb.domain.repository.mapper.base.QuizMapper;
import quizweb.domain.service.CreateQuizService;

@SpringBootTest
public class CreateQuizServiceTest {

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Autowired    
    CreateQuizService target;
    @MockBean(name = "quizMapper")
    QuizMapper quizMapper;
    @MockBean
    SecurityContext securityContext;
    @MockBean
    Authentication authentication;


}
