package quizweb.repository.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import quizweb.common.enums.ChoiceTypeEnum;
import quizweb.domain.repository.entity.StagingChoice;
import quizweb.domain.repository.entity.StagingQuestion;
import quizweb.domain.repository.entity.StagingQuiz;
import quizweb.domain.repository.mapper.CreateQuizParamMapper;
import quizweb.domain.repository.mapper.base.StagingChoiceMapper;
import quizweb.domain.repository.mapper.base.StagingQuestionMapper;
import quizweb.domain.repository.mapper.base.StagingQuizMapper;
import quizweb.object.CreateChoiceParam;
import quizweb.object.CreateQuestionParam;
import quizweb.object.CreateQuizParam;

@SpringBootTest
@Transactional
public class CreateQuizParamMapperTest {
    @Autowired
    private StagingQuizMapper stagingQuizMapper;

    @Autowired
    private StagingQuestionMapper stagingQuestionMapper;

    @Autowired
    private StagingChoiceMapper stagingChoiceMapper;

    @Autowired
    private CreateQuizParamMapper target;

    private long QUIZ_ID = 1L;

    private String QUIZ_DESCRIPTION = "description";

    private String QUIZ_THUBNAIL = "image";

    private String TITLE = "TITLE";

    private long[] QUESTION_ID = { 10L, 20L, 30L };

    private String[] QUESTION_CONTENT = { "CONTENT_1", "CONTENT_2", "CONTENT_3" };

    private String[] QUESTION_COMMENT = { "COMMENT_1", "COMMENT_2", "COMMENT_3" };

    private ChoiceTypeEnum[] CHOICE_TYPE = { ChoiceTypeEnum.single, ChoiceTypeEnum.image,
            ChoiceTypeEnum.single };

    private long[][] CHOICE_ID = { { 101L, 102L, 103L, 104L }, { 201L, 202L, 203L, 204L }, { 301L, 302L, 303L, 304L } };

    private String[][] CHOICE_CONTENT = { { "1-1", "1-2", "1-3", "1-4" }, { "2-1", "2-2", "2-3", "2-4" },
            { "3-1", "3-2", "3-3", "3-4" } };

    @Test
    public void getCreateParamTest() {
        StagingQuiz stQuiz = new StagingQuiz();
        stQuiz.setQuizId(QUIZ_ID);
        stQuiz.setDescription(QUIZ_DESCRIPTION);
        stQuiz.setThumbnail(QUIZ_THUBNAIL);
        stQuiz.setTitle(TITLE);
        stagingQuizMapper.insert(stQuiz);
        for (int questionIndex = 0; questionIndex < QUESTION_ID.length; questionIndex++) {
            StagingQuestion stQuestion = new StagingQuestion();
            stQuestion.setQuizId(QUIZ_ID);
            stQuestion.setQuestionId(QUESTION_ID[questionIndex]);
            stQuestion.setNum(questionIndex);
            stQuestion.setContent(QUESTION_CONTENT[questionIndex]);
            stQuestion.setComment(QUESTION_COMMENT[questionIndex]);
            stQuestion.setChoicetype(CHOICE_TYPE[questionIndex].name());
            stagingQuestionMapper.insert(stQuestion);
            for (int choiceIndex = 0; choiceIndex < CHOICE_ID[questionIndex].length; choiceIndex++) {
                StagingChoice stChoice = new StagingChoice();
                stChoice.setQuizId(QUIZ_ID);
                stChoice.setQuestionId(QUESTION_ID[questionIndex]);
                stChoice.setChoiceId(CHOICE_ID[questionIndex][choiceIndex]);
                stChoice.setContent(CHOICE_CONTENT[questionIndex][choiceIndex]);
                stChoice.setCorrectFlg(questionIndex == choiceIndex);
                stChoice.setSelectionNo(choiceIndex);
                stagingChoiceMapper.insert(stChoice);
            }
        }
        CreateQuizParam act = target.getCreateQuizParam(QUIZ_ID);
        assertEquals(QUIZ_ID, act.getId());
        assertEquals(QUIZ_DESCRIPTION, act.getDescription());
        assertEquals(QUIZ_THUBNAIL, act.getThumbnail());
        for (int questionIndex = 0; questionIndex < act.getQuestions().size(); questionIndex++) {
            CreateQuestionParam actQuestion = act.getQuestions().get(questionIndex);
            assertEquals(QUESTION_ID[questionIndex], actQuestion.getId());
            assertEquals(questionIndex, actQuestion.getNum());
            assertEquals(QUESTION_CONTENT[questionIndex], actQuestion.getContent());
            assertEquals(QUESTION_COMMENT[questionIndex], actQuestion.getComment());
            assertEquals(CHOICE_TYPE[questionIndex], actQuestion.getChoicetype());
            for (int choiceIndex = 0; choiceIndex < actQuestion.getChoices().size(); choiceIndex++) {
                CreateChoiceParam actChoice = actQuestion.getChoices().get(choiceIndex);
                assertEquals(CHOICE_ID[questionIndex][choiceIndex], actChoice.getId());
                assertEquals(choiceIndex, actChoice.getSelectionNo());
                assertEquals(CHOICE_CONTENT[questionIndex][choiceIndex], actChoice.getContent());
                assertEquals(questionIndex == choiceIndex, actChoice.getCorrectFlg());
            }
        }
    }
}
