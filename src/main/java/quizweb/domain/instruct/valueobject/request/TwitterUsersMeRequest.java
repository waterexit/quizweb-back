package quizweb.domain.instruct.valueobject.request;

import java.util.Map.Entry;

import org.springframework.http.RequestEntity;

import quizweb.common.enums.HttpRequestMethodEnum;
import quizweb.common.properties.TwitterAPIUrlProperties;
import quizweb.domain.instruct.valueobject.base.RequestObject;

public class TwitterUsersMeRequest extends RequestObject {

    private RequestEntity<Void> requestEntity;

    public TwitterUsersMeRequest(TwitterAPIUrlProperties twitterAPIUrlProperties, String bearer) {
        this.httpMethod = HttpRequestMethodEnum.GET;
        this.url = twitterAPIUrlProperties.getUsersMe();
        createRequestEntity(bearer);
    }

    private void createRequestEntity(String bearer) {
        StringBuilder urlBuilder = new StringBuilder(url);
        for (Entry<String, String> e : param.entrySet()) {
            urlBuilder.append("?" + e.getKey() + "=" + e.getValue());
        }
        requestEntity = RequestEntity.get(url).header("Authorization", "Bearer " + bearer).build();
    }

    public RequestEntity<Void> getRequetEntity() {
        return requestEntity;
    }

}
