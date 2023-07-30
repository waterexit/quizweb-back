package quizweb.domain.instruct.valueobject.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import quizweb.domain.instruct.valueobject.base.ResponseObject;

public class TwitterOauth2TokenResponse extends ResponseObject {

    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private String expiresIn;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("reflesh_token")
    private String refleshToken;

    public String getTokenType() {
        return tokenType;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getScope() {
        return scope;
    }

    public String getRefleshToken() {
        return refleshToken;
    }
}
