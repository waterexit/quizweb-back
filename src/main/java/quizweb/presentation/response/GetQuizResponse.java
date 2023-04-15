package quizweb.presentation.response;

import java.util.List;

import quizweb.domain.service.valueobject.QuizInfo;

public class GetQuizResponse {
    public GetQuizResponse(List<QuizInfo> quizInfoList, int count) {
        this.quizInfoList = quizInfoList;
        this.count = count;
    }

    private List<QuizInfo> quizInfoList;
    private int count;

    public List<QuizInfo> getQuizInfoList() {
        return quizInfoList;
    }

    public int getCount() {
        return count;
    }
}
