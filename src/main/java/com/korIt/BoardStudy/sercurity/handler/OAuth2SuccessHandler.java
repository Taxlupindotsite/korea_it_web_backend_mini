package com.korIt.BoardStudy.sercurity.handler;

// 로그인 후 인증객체 만들어 놓은 후, 어떻게 처리할지
// 여기서 확인. 연동된적 있으면 토큰 발급해서 클라에 넘기고,
// 아니면 provider...

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        String provider = defaultOAuth2User.getAttribute("provider");
        String providerUserId = defaultOAuth2User.getAttribute("id");
        String email = defaultOAuth2User.getAttribute("email");

//      조회해야함, 매퍼 만들기



    }
}
