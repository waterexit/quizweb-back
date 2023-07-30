package quizweb.app.authentic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import quizweb.domain.service.AuthOnTwitterOauth2Service;

@Component
public class MyAuthenticationOAuth2UserDetailsService
                implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

        @Autowired
        AuthOnTwitterOauth2Service authOnTwitterOauth2Service;

        @Override
        public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {

                // String oauthToken = (String) token.getPrincipal();
                String code = (String) token.getCredentials();

                // TwitterAccessTokenResponse twitterAccessTokenResponse = twitterAccessTokenGateway.fetchAPI(oauthToken,
                //                 oauthVerifier);

                // TwitterVerifyCredentialsResponse twitterVerifyCredentialsResponse = twitterVerifyCredentialsGateway
                //                 .fetchAPI(twitterAccessTokenResponse.getOauthToken(),
                //                                 twitterAccessTokenResponse.getOauthTokenSecret());

                // TwitterUser twitterUser = new TwitterUser(twitterVerifyCredentialsResponse);

                return authOnTwitterOauth2Service.authTwitterUser(code);
        }
}
