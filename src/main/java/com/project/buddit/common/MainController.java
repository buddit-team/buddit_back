package com.project.buddit.common;

import com.project.buddit.utill.kakao.KakaoAPI;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
public class MainController {

    @Autowired
    private KakaoAPI kakao;

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        kakao.kakaoLogout((String) session.getAttribute("access_Token"));
        session.removeAttribute("access_Token");
        session.removeAttribute("userId");
        return "common/main.html";
    }

    @RequestMapping(value = "/login")
    public String login(@RequestParam("code") String code, HttpSession session) {
        String access_Token = kakao.getAccessToken(code);
        HashMap<String, Object> userInfo = kakao.getUserInfo(access_Token);
        System.out.println("login Controller : " + userInfo);

        //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        session.setAttribute("userId", userInfo.get("email"));
        session.setAttribute("access_Token", access_Token);

        return "common/main.html";
    }

    @GetMapping("/")
    public String main() {
        return "common/main.html";
    }
}
