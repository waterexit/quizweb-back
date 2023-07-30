package quizweb.domain.service;

import quizweb.app.authentic.entity.TwitterUser;

public interface AuthOnTwitterOauth2Service {

    public String generateAuthURL();

    public TwitterUser authTwitterUser(String code);

}
