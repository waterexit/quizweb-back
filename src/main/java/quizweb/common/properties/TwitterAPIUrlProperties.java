package quizweb.common.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TwitterAPIUrlProperties {
    @Value("${request_token}")
    private String requestToken;

    @Value("${authenticate}")
    private String authenticate;

    @Value("${access_token}")
    private String accessToken;

    @Value("${verify_credentials}")
    private String verifyCredentials;

    @Value("${authenticate_oauth2}")
    private String authenticateOAuth2;

    @Value("${access_token_oauth2}")
    private String accessTokenOAuth2;

    @Value("${users_me}")
    private String usersMe;

    public String getRequestToken() {
        return requestToken;
    }

    public String getAuthenticate() {
        return authenticate;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getVerifyCredentials() {
        return verifyCredentials;
    }

    public String getAuthenticateOAuth2() {
        return authenticateOAuth2;
    }

    public String getAccessTokenOAuth2() {
        return accessTokenOAuth2;
    }

    public String getUsersMe() {
        return usersMe;
    }
}
