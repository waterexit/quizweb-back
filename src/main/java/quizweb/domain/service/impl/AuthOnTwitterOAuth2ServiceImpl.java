package quizweb.domain.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import quizweb.app.authentic.entity.TwitterUser;
import quizweb.common.properties.TwitterAPITokenProperties;
import quizweb.common.properties.TwitterAPIUrlProperties;
import quizweb.domain.instruct.gateway.TwitterOauth2TokenGateway;
import quizweb.domain.instruct.gateway.TwitterUsersMeGateway;
import quizweb.domain.instruct.valueobject.response.TwitterOauth2TokenResponse;
import quizweb.domain.service.AuthOnTwitterOauth2Service;

@Service
public class AuthOnTwitterOAuth2ServiceImpl implements AuthOnTwitterOauth2Service {

    @Autowired
    public AuthOnTwitterOAuth2ServiceImpl(TwitterAPITokenProperties twitterAPITokenProperties,
            TwitterAPIUrlProperties twitterAPIUrlProperties, TwitterOauth2TokenGateway twitterOauth2TokenGateway,
            TwitterUsersMeGateway twitterUsersMeGateway) {
        this.twitterAPITokenProperties = twitterAPITokenProperties;
        this.twitterAPIUrlProperties = twitterAPIUrlProperties;
        this.twitterOauth2TokenGateway = twitterOauth2TokenGateway;
        this.twitterUsersMeGateway = twitterUsersMeGateway;
        this.codeVerifier = RandomStringUtils.randomAlphanumeric(50);
    }

    TwitterAPITokenProperties twitterAPITokenProperties;
    TwitterAPIUrlProperties twitterAPIUrlProperties;
    TwitterOauth2TokenGateway twitterOauth2TokenGateway;
    TwitterUsersMeGateway twitterUsersMeGateway;
    private String codeVerifier;

    @Value("${callback_url}")
    public String callbackURL;

    @Override
    public String generateAuthURL() {
        String authUrl = twitterAPIUrlProperties.getAuthenticateOAuth2();
        // TODO: CSRF対策なのでspringからトークンを取る必要あり。テスト用に固定値にしているが意味なし。callbackで検証すること。
        String state = "abc";

        return UriComponentsBuilder.fromUriString(authUrl)
                .queryParam("response_type", "code")
                .queryParam("client_id", twitterAPITokenProperties.getAPIKey())
                .queryParam("redirect_uri", callbackURL)
                // .queryParam("scope", "tweet.read")
                .queryParam("scope", "tweet.read%20users.read")
                .queryParam("state", state)
                .queryParam("code_challenge", codeVerifier)
                .queryParam("code_challenge_method", "plain")
                .build()
                .toUriString();
    }

    @Override
    public TwitterUser authTwitterUser(String code) {
        TwitterOauth2TokenResponse res = twitterOauth2TokenGateway.fetchAPI(callbackURL, code, codeVerifier);
        String bearerToken = res.getAccessToken();
        String json = twitterUsersMeGateway.fetchAPI(bearerToken);
        return new TwitterUser(json);
    }

}
