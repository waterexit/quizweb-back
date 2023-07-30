package quizweb.domain.instruct.valueobject.request;

import java.util.Base64;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import quizweb.common.enums.HttpRequestMethodEnum;
import quizweb.common.properties.TwitterAPITokenProperties;
import quizweb.common.properties.TwitterAPIUrlProperties;
import quizweb.domain.instruct.valueobject.base.RequestObject;

public class TwitterOauth2TokenRequest extends RequestObject {
    private RequestEntity<MultiValueMap<String, String>> requestEntity;

    public TwitterOauth2TokenRequest(TwitterAPITokenProperties twitterAPITokenProperties,
            TwitterAPIUrlProperties twitterAPIUrlProperties, String callBackUri, String oauthCode,
            String codeVerifier) {
        this.httpMethod = HttpRequestMethodEnum.POST;
        this.url = twitterAPIUrlProperties.getAccessTokenOAuth2();
        param.put("grant_type", "authorization_code");
        param.put("client_id", twitterAPITokenProperties.getAPIKey());
        param.put("redirect_uri", callBackUri);
        param.put("code", oauthCode);
        param.put("code_verifier", codeVerifier);
        createRequestEntity(twitterAPITokenProperties);
    }

    private void createRequestEntity(TwitterAPITokenProperties tApiTokenProperties) {

        String userPassword = new String(
                Base64.getEncoder().encode(
                        (tApiTokenProperties.getAPIKey() + ":" + tApiTokenProperties.getAPIKeySecret()).getBytes()));
        StringBuilder basicAuth = new StringBuilder();
        basicAuth.append("Basic ").append(userPassword);

        MultiValueMap<String, String> reqParam = new LinkedMultiValueMap<>();
        param.entrySet().forEach(e -> reqParam.add(e.getKey(), e.getValue()));


        requestEntity = RequestEntity.post(url).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", basicAuth.toString()).body(reqParam);
    }

    public RequestEntity<MultiValueMap<String, String>> getRequetEntity() {
        return requestEntity;
    }

}
