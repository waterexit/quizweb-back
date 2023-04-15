package quizweb.domain.service;

import quizweb.common.exception.AuthFailException;

public interface AddTagService {
    public void addTag(long quizId, String tag) throws AuthFailException;
}
