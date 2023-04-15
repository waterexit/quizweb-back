package quizweb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import quizweb.common.enums.ChoiceTypeEnum;
import quizweb.common.exception.AuthFailException;
import quizweb.domain.repository.entity.Choice;
import quizweb.domain.repository.entity.Question;
import quizweb.domain.repository.entity.Quiz;
import quizweb.domain.repository.entity.QuizTagging;
import quizweb.domain.repository.entity.Tag;
import quizweb.domain.repository.mapper.ExpandChoiceMapper;
import quizweb.domain.repository.mapper.ExpandQuestionMapper;
import quizweb.domain.repository.mapper.QuizTaggingMapper;
import quizweb.domain.repository.mapper.TagMapper;
import quizweb.domain.repository.mapper.base.QuizMapper;
import quizweb.domain.service.EditQuizService;
import quizweb.object.CreateChoiceParam;
import quizweb.object.CreateQuestionParam;
import quizweb.object.CreateQuizParam;

@SpringBootTest
public class EditQuizServiceTest {

    @Autowired
    EditQuizService target;

    @Autowired
    private QuizMapper quizMapper;
    @Autowired
    private ExpandQuestionMapper questionMapper;
    @Autowired
    private ExpandChoiceMapper choiceMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private QuizTaggingMapper quizTaggingMapper;

    @BeforeEach
    public void setup() {
    }

    @Test
    @Transactional
    public void getParamTest() throws AuthFailException {
        final long QUIZ_ID = 99999l;
        final String TITLE = "タイトルテスト";
        final String DESCRIPTION = "説明テスト";
        final String THUMBNAIL = "";
        insertQuiz(QUIZ_ID, TITLE, DESCRIPTION, THUMBNAIL);

        long[] QUESTION_ID_ARRAY = { 88888l, 88889l, 88890l, 88891l };
        String[] QUESTION_CONTENT_ARRAY = { "question1", "question2", "question3", null };
        String[] QUESTION_COMMENT_ARRAY = { "comment1", "comment2", "comment3", null };
        ChoiceTypeEnum[] CHOICE_TYPE = { ChoiceTypeEnum.single, ChoiceTypeEnum.image };

        long[] CHOICE_ID_BASE_ARRAY = { 300l, 400l };
        String[] CHOICE_CONTENT_BASE_ARRAY = { "choice1", "choice2" };
        for (int i = 0; i < 4; i++) {
            insertQuestion(QUIZ_ID, QUESTION_ID_ARRAY[i], QUESTION_CONTENT_ARRAY[i], QUESTION_COMMENT_ARRAY[i],
                    CHOICE_TYPE[i % 2].name());
            for (int j = 0; j < 2; j++) {
                long questionId = QUESTION_ID_ARRAY[i];
                String questionContent = QUESTION_CONTENT_ARRAY[i];
                insertChoice(QUIZ_ID, QUESTION_ID_ARRAY[i], questionId + CHOICE_ID_BASE_ARRAY[j],
                        questionContent + "_" + CHOICE_CONTENT_BASE_ARRAY[j], j, j == 1);
            }
        }

        long[] TAG_ID = { 111111l, 222222l };
        String[] TAG = { "tag1", "tag2" };
        insertTag(TAG_ID[0], TAG[0]);
        insertTag(TAG_ID[1], TAG[1]);

        insertQuizTagging(QUIZ_ID, TAG_ID[1]);

        CreateQuizParam act = null;
        act = target.getDataForEdit(QUIZ_ID);

        assertEquals(QUIZ_ID, act.getId());
        assertEquals(TITLE, act.getTitle());
        assertEquals(DESCRIPTION, act.getDescription());

        List<CreateQuestionParam> actQuestionList = act.getQuestions();
        assertEquals(4, actQuestionList.size());
        for (int i = 0; i < 4; i++) {
            CreateQuestionParam assertedQuestion = actQuestionList.get(i);
            assertEquals(QUESTION_CONTENT_ARRAY[i], assertedQuestion.getContent());
            assertEquals(QUESTION_COMMENT_ARRAY[i], assertedQuestion.getComment());
            // assertEquals(CHOICE_TYPE[i % 2], assertedQuestion.getChoiceType());
            List<CreateChoiceParam> actChoiceList = assertedQuestion.getChoices();
            for (int j = 0; j < 2; j++) {
                CreateChoiceParam asseertedChocie = actChoiceList.get(j);
                assertEquals(QUESTION_CONTENT_ARRAY[i] + "_" + CHOICE_CONTENT_BASE_ARRAY[j],
                        asseertedChocie.getContent());
                assertEquals(j == 1, asseertedChocie.getCorrectFlg());
            }
        }

    }

    private void insertQuiz(long quizId, String title, String description, String thumbnail) {
        Quiz quiz = new Quiz();
        quiz.setId(quizId);
        quiz.setTitle(title);
        quiz.setDescription(description);
        quiz.setThumbnail(thumbnail);
        quizMapper.insert(quiz);

    }

    private void insertQuestion(long quizId, long questionId, String content, String comment, String choiceType) {
        Question question = new Question();
        question.setQuizId(quizId);
        question.setId(questionId);
        question.setContent(content);
        question.setComment(comment);
        question.setChoicetype(choiceType);
        questionMapper.insert(question);
    }

    private void insertChoice(long quizId, long questionId, long choiceId, String content, int selectionNo,
            boolean correctFlg) {
        Choice choice = new Choice();
        choice.setQuizId(quizId);
        choice.setQuestionId(questionId);
        choice.setId(choiceId);
        choice.setContent(content);
        choice.setSelectionNo(selectionNo);
        choice.setCorrectFlg(correctFlg);
        choiceMapper.insert(choice);
    }

    private void insertTag(long tagId, String tagContent) {
        Tag tag = new Tag();
        tag.setId(tagId);
        tag.setTag(tagContent);
        tagMapper.insert(tag);
    }

    private void insertQuizTagging(long quizId, long tagId) {
        QuizTagging quizTagging = new QuizTagging();
        quizTagging.setQuizId(quizId);
        quizTagging.setTagId(tagId);
        quizTaggingMapper.insert(quizTagging);
    }
}