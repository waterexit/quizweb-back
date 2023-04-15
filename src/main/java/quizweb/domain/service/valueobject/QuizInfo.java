package quizweb.domain.service.valueobject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import quizweb.domain.repository.entity.Quiz;
import quizweb.domain.repository.entity.Tag;
import quizweb.domain.repository.entity.TaggingInfo;

public class QuizInfo {
    public QuizInfo(Quiz quiz, List<TaggingInfo> taggingInfos) {
        this.quiz = quiz;
        if (taggingInfos != null) {
            List<Tag> tags = taggingInfos.stream().map(ti -> {
                Tag t = new Tag();
                t.setId(ti.getTagId());
                t.setTag(ti.getTag());
                t.setNum(ti.getNum());
                return t;
            }).collect(Collectors.toList());
            this.tags = tags;
        } else {
            this.tags = new ArrayList<>();
        }
    }

    private Quiz quiz;

    private List<Tag> tags;

    public Quiz getQuiz() {
        return quiz;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
