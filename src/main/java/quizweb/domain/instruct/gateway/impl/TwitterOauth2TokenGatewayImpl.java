package quizweb.domain.instruct.gateway.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import quizweb.common.properties.TwitterAPITokenProperties;
import quizweb.common.properties.TwitterAPIUrlProperties;
import quizweb.domain.instruct.gateway.TwitterOauth2TokenGateway;
import quizweb.domain.instruct.valueobject.request.TwitterOauth2TokenRequest;
import quizweb.domain.instruct.valueobject.response.TwitterOauth2TokenResponse;

@Component
public class TwitterOauth2TokenGatewayImpl implements TwitterOauth2TokenGateway {
    private RestTemplate restTemplate;
    private TwitterAPITokenProperties twitterAPITokenProperties;
    private TwitterAPIUrlProperties twitterAPIUrlProperties;

    @Autowired
    public TwitterOauth2TokenGatewayImpl(RestTemplate restTemplate,
            TwitterAPITokenProperties twitterAPITokenProperties, TwitterAPIUrlProperties twitterAPIUrlProperties) {
        this.restTemplate = restTemplate;
        this.twitterAPITokenProperties = twitterAPITokenProperties;
        this.twitterAPIUrlProperties = twitterAPIUrlProperties;
    }

    @Override
    public TwitterOauth2TokenResponse fetchAPI(String callBackUri, String code, String codeVerifier) {
        TwitterOauth2TokenRequest request = new TwitterOauth2TokenRequest(twitterAPITokenProperties,
                twitterAPIUrlProperties,
                callBackUri, code, codeVerifier);
        TwitterOauth2TokenResponse response = null;
        try {
            ResponseEntity<TwitterOauth2TokenResponse> responseEntity = restTemplate.exchange(request.getRequetEntity(),
                    TwitterOauth2TokenResponse.class);
            response = responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
