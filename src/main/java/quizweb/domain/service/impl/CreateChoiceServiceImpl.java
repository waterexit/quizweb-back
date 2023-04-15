package quizweb.domain.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import quizweb.app.authentic.entity.TwitterUser;
import quizweb.common.constant.Constants;
import quizweb.common.enums.ChoiceTypeEnum;
import quizweb.common.properties.ApplicationProperties;
import quizweb.common.util.FileUtil;
import quizweb.domain.repository.entity.Choice;
import quizweb.domain.repository.entity.StagingChoice;
import quizweb.domain.repository.mapper.base.ChoiceMapper;
import quizweb.domain.repository.mapper.base.StagingChoiceMapper;
import quizweb.domain.service.CreateChoiceService;
import quizweb.object.CreateChoiceParam;

@Service
public class CreateChoiceServiceImpl implements CreateChoiceService {

    @Autowired
    public CreateChoiceServiceImpl(ChoiceMapper choiceMapper,StagingChoiceMapper stagingChoiceMapper ,ApplicationProperties applicationProperties) {
        this.choiceMapper = choiceMapper;
        this.stagingChoiceMapper = stagingChoiceMapper;
        this.applicationProperties = applicationProperties;
    }

    private ChoiceMapper choiceMapper;
    private StagingChoiceMapper stagingChoiceMapper;
    private ApplicationProperties applicationProperties;

    @Override
    public Choice createChoice(long quizId, long questionId, int selectionNo, boolean correctFlg) {
        Choice choice = new Choice();
        choice.setQuizId(quizId);
        choice.setQuestionId(questionId);
        choice.setSelectionNo(selectionNo);
        choice.setCorrectFlg(correctFlg);
        Object authInfo = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (authInfo instanceof TwitterUser) {
            choice.setCreateUserid(((TwitterUser) authInfo).getUserId());
            choiceMapper.insert(choice);
            StagingChoice stagingChoice = new StagingChoice();
            stagingChoice.setChoiceId(choice.getId());
            stagingChoice.setCorrectFlg(correctFlg);
            stagingChoice.setSelectionNo(selectionNo);
            stagingChoiceMapper.insert(stagingChoice);
        } else {
            choice.setCreateUserid(Constants.GUEST_ID);
            // HACK: AUTOINCREMENTの値を取るためにinsertの後にdelete
            choiceMapper.insert(choice);
            choiceMapper.deleteByPrimaryKey(choice.getId());
        }
        return choice;
    }

    @Override
    public void insertChoices(ChoiceTypeEnum choiceType, List<CreateChoiceParam> params) throws IOException {
        switch (choiceType) {
            case single:
                insertSingleChoice(params);
                break;
            case image:
                insertImageChoice(params);
                break;
        }
    }

    private void insertSingleChoice(List<CreateChoiceParam> params) {
        for (int i = 0; i < params.size(); i++) {
            CreateChoiceParam choiceParam = params.get(i);

            Choice choice = new Choice();
            choice.setQuestionId(choiceParam.getQuestionId());
            choice.setContent(choiceParam.getContent());
            choice.setCorrectFlg(choiceParam.getCorrectFlg());
            choice.setSelectionNo(i);

            choiceMapper.insert(choice);
        }

    }

    private void insertImageChoice(List<CreateChoiceParam> params) throws IOException {
        for (int i = 0; i < params.size(); i++) {
            CreateChoiceParam choiceParam = params.get(i);
            Choice choice = new Choice();
            choice.setSelectionNo(i);
            choice.setQuestionId(choiceParam.getQuestionId());
            // TODO:Filterみたいなのでリクエストから飛んでくるときに自動で変換したい（ImageStringみたいなオリジナルの型作ってやる？）
            String fileName = FileUtil.saveImageByBase64(applicationProperties.getImageChoicePath(),
                    choiceParam.getContent());
            choice.setContent(fileName);
            choice.setCorrectFlg(choiceParam.getCorrectFlg());

            choiceMapper.insert(choice);

        }
    }

}
