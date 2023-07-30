package quizweb.domain.instruct.gateway.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import quizweb.common.properties.TwitterAPIUrlProperties;
import quizweb.domain.instruct.gateway.TwitterUsersMeGateway;
import quizweb.domain.instruct.valueobject.request.TwitterUsersMeRequest;

@Component
public class TwitterUsersMeGatewayImpl implements TwitterUsersMeGateway {

    private RestTemplate restTemplate;
    // private TwitterAPITokenProperties twitterAPITokenProperties;
    private TwitterAPIUrlProperties twitterAPIUrlProperties;

    @Autowired
    public TwitterUsersMeGatewayImpl(RestTemplate restTemplate, TwitterAPIUrlProperties twitterAPIUrlProperties) {
        this.restTemplate = restTemplate;
        // this.twitterAPITokenProperties = twitterAPITokenProperties;
        this.twitterAPIUrlProperties = twitterAPIUrlProperties;
    }

    @Override
    public String fetchAPI(String bearerToken) {
        TwitterUsersMeRequest req = new TwitterUsersMeRequest(twitterAPIUrlProperties, bearerToken);
        ResponseEntity<String> res = restTemplate.exchange(req.getRequetEntity(), String.class);
        return res.getBody();
    }

}
