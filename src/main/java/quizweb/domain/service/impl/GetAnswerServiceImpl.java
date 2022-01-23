package quizweb.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quizweb.domain.repository.entity.Choice;
import quizweb.domain.repository.mapper.ExpandChoiceMapper;
import quizweb.domain.service.GetAnwerService;

@Service
public class GetAnswerServiceImpl implements GetAnwerService {
    @Autowired
    public GetAnswerServiceImpl(ExpandChoiceMapper choiceMapper) {
        this.choiceMapper = choiceMapper;

    }

    ExpandChoiceMapper choiceMapper;

    @Override
    public int getAnswer(long quiestionId) {
       Choice correctChoice = choiceMapper.selectCorrectChoice(quiestionId);

        return correctChoice.getSelectionNo();
        
    }

}
