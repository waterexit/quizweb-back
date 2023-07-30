package quizweb.domain.instruct.gateway;

import quizweb.domain.instruct.valueobject.response.TwitterOauth2TokenResponse;

public interface TwitterOauth2TokenGateway {
    public TwitterOauth2TokenResponse fetchAPI(String callBackUri, String code, String codeVerifier);
}
