package quizweb.domain.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import quizweb.app.authentic.entity.TwitterUser;
import quizweb.common.exception.AuthFailException;
import quizweb.common.properties.ApplicationProperties;
import quizweb.common.util.FileUtil;
import quizweb.domain.repository.entity.Choice;
import quizweb.domain.repository.entity.QuestionDetail;
import quizweb.domain.repository.entity.Quiz;
import quizweb.domain.repository.entity.QuizDetail;
import quizweb.domain.repository.mapper.QuizDetailMapper;
import quizweb.domain.repository.mapper.TagMapper;
import quizweb.domain.repository.mapper.TaggingInfoMapper;
import quizweb.domain.repository.mapper.base.QuizMapper;
import quizweb.domain.service.EditQuizService;
import quizweb.object.CreateChoiceParam;
import quizweb.object.CreateQuestionParam;
import quizweb.object.CreateQuizParam;

@Service
public class EditQuizServiceImpl implements EditQuizService {
    @Autowired
    public EditQuizServiceImpl(QuizMapper quizMapper, QuizDetailMapper quizDetailMapper,
            ApplicationProperties applicationProperties, TaggingInfoMapper taggingInfoMapper, TagMapper tagMapper) {
        this.quizMapper = quizMapper;
        this.quizDetailMapper = quizDetailMapper;
        this.applicationProperties = applicationProperties;
        this.taggingInfoMapper = taggingInfoMapper;
        this.tagMapper = tagMapper;
    }

    private QuizMapper quizMapper;
    private QuizDetailMapper quizDetailMapper;
    private ApplicationProperties applicationProperties;
    private TaggingInfoMapper taggingInfoMapper;
    private TagMapper tagMapper;

    @Override
    public CreateQuizParam getDataForEdit(long id) throws AuthFailException {
        authUserInfo(id);

        QuizDetail quizDetail = quizDetailMapper.getQuizDetail(id);
        CreateQuizParam ret = new CreateQuizParam();
        ret.setId(quizDetail.getId());
        ret.setTitle(quizDetail.getTitle());
        ret.setDescription(quizDetail.getDescription());
        ret.setThumbnail(quizDetail.getThumbnail());
        ret.setTags(quizDetail.getTags());
        List<CreateQuestionParam> retQuestionList = new ArrayList<>();
        for (QuestionDetail question : quizDetail.getQuestions()) {
            CreateQuestionParam tmpQuestion = new CreateQuestionParam();
            tmpQuestion.setContent(question.getContent());
            tmpQuestion.setComment(question.getComment());
            tmpQuestion.setChoiceType(question.getChoicetype());
            List<CreateChoiceParam> retChoiceList = new ArrayList<>();
            for (Choice choice : question.getChoices()) {
                CreateChoiceParam tmpChoice = new CreateChoiceParam();
                tmpChoice.setContent(choice.getContent());
                tmpChoice.setCorrectFlg(choice.getCorrectFlg());
                retChoiceList.add(tmpChoice);
            }
            tmpQuestion.setChoices(retChoiceList);
            retQuestionList.add(tmpQuestion);
        }
        ret.setQuestions(retQuestionList);

        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editQuiz(Quiz quiz) throws IOException, AuthFailException {
        authUserInfo(quiz.getId());

        if (quiz.getThumbnail() != null) {
            String fileName = FileUtil.saveImageByBase64(applicationProperties.getImageThumbnailPath(),
                    quiz.getThumbnail());
            quiz.setThumbnail(fileName);
        }
        quizMapper.updateByPrimaryKeySelective(quiz);
    }

    @Override
    public String publish(Long quizId) throws AuthFailException {
        authUserInfo(quizId);

        Quiz quiz = new Quiz();
        quiz.setId(quizId);
        quiz.setPublish(true);
        quizMapper.updateByPrimaryKeySelective(quiz);

        List<Long> tagIdList = taggingInfoMapper.getTaggingInfoByQuizId(quizId).stream()
                .map(taginf -> taginf.getTagId()).collect(Collectors.toList());
        if (tagIdList != null && tagIdList.size() != 0) {
            tagMapper.updateNum(tagIdList);
        }
        String title = quizMapper.selectByPrimaryKey(quizId).getTitle();

        return title;
    }

    private void authUserInfo(long quizId) throws AuthFailException {
        TwitterUser twitterUser = (TwitterUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Quiz quiz = quizMapper.selectByPrimaryKey(quizId);
        if (!quiz.getCreateUserid().equals(twitterUser.getUserId())) {
            System.err.println(quiz.getCreateUserid() + ":" + twitterUser.getUserId());
            throw new AuthFailException();
        }
    }

}
