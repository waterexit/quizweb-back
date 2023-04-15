package quizweb.domain.service;

import java.io.IOException;

import quizweb.object.CreateQuizParam;

public interface InsertInBulkService {
    public void insertInBulk(CreateQuizParam createQuizParam) throws IOException;
}
