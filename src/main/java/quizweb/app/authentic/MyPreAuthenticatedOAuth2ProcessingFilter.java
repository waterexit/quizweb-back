package quizweb.app.authentic;


import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class MyPreAuthenticatedOAuth2ProcessingFilter extends AbstractPreAuthenticatedProcessingFilter{

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest req) {
        // TODO: CSRF対策の認証入れるならこれを使う。twitterサーバ側でcode challengeで検証済なのでとりあえず未実装
        String state = req.getParameter("state");
        return state;
    }


    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest req) {
        String oauthVerifier = req.getParameter("code");
        return oauthVerifier;
    }

}
