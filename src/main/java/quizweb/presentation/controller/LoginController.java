package quizweb.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import quizweb.app.authentic.entity.TwitterUser;
import quizweb.domain.repository.entity.User;
import quizweb.domain.service.FindOrResisterUserService;

@Controller
public class LoginController {

    FindOrResisterUserService findOrResisterUserSevice;

    @Value("${after_login_url}")
    private String afterLoginUrl;

    @Autowired
    public LoginController(FindOrResisterUserService findOrResisterUserSevice) {
        this.findOrResisterUserSevice = findOrResisterUserSevice;
    }

    @GetMapping("/login")
    public String setLoginInfoAndRedirect() {
        // localhostじゃないとsessionが保存されない、なぜ？127.0.0.1だとダメ
        // 多分callbackがlocalhostだからでは（これが理由だった）
        // https://stackoverflow.com/questions/4694089/sending-browser-cookies-during-a-302-redirect
        return "redirect:" + afterLoginUrl;
    }

    @PostMapping("/login")
    @ResponseBody
    public User login() {
        TwitterUser twitterUser = (TwitterUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = findOrResisterUserSevice.findOrResisterUserByTwitteUser(twitterUser);

        return user;
    }

}
