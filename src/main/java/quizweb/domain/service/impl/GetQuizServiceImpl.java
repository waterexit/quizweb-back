package quizweb.domain.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quizweb.domain.repository.entity.Quiz;
import quizweb.domain.repository.entity.TaggingInfo;
import quizweb.domain.repository.mapper.ExpandQuizMapper;
import quizweb.domain.repository.mapper.TaggingInfoMapper;
import quizweb.domain.service.GetQuizService;
import quizweb.domain.service.valueobject.QuizInfo;
import quizweb.presentation.request.GetQuizRequest;

@Service
public class GetQuizServiceImpl implements GetQuizService {

    @Autowired
    public GetQuizServiceImpl(ExpandQuizMapper quizMapper, TaggingInfoMapper taggingInfoMapper) {
        this.quizMapper = quizMapper;
        this.taggingInfoMapper = taggingInfoMapper;
    }

    ExpandQuizMapper quizMapper;

    TaggingInfoMapper taggingInfoMapper;

    @Override
    public int getCountSearchedQuiz(GetQuizRequest request) {
        int count = quizMapper.count(request.getSearchCondition());
  
        return count;
    }

    @Override
    public List<QuizInfo> getQuizList(GetQuizRequest request) {
        int limit = request.getFetchSize();
        int offset = request.getFetchSize() * (request.getPage() - 1);

        List<Quiz> quizList = quizMapper.fetchQuizes(request.getSearchCondition(), limit, offset);
        List<TaggingInfo> tagList = taggingInfoMapper.getTaggingInfoByQuizList(quizList);

        Map<Long, List<TaggingInfo>> tagMap = tagList.stream().collect(Collectors.groupingBy(TaggingInfo::getQuizId,
                Collectors.toList()));

        List<QuizInfo> resList = new ArrayList<>();

        quizList.stream().forEach(q -> resList.add(new QuizInfo(q, tagMap.get(q.getId()))));

        return resList;
    }

}