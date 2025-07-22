package com.korIt.BoardStudy.sercurity.handler;

// 로그인 후 인증객체 만들어 놓은 후, 어떻게 처리할지
// 여기서 확인. 연동된적 있으면 토큰 발급해서 클라에 넘기고,
// 아니면 provider...

import com.korIt.BoardStudy.entity.OAuth2User;
import com.korIt.BoardStudy.entity.User;
import com.korIt.BoardStudy.repository.OAuth2UserRepository;
import com.korIt.BoardStudy.repository.UserRepository;
import com.korIt.BoardStudy.sercurity.jwt.JwtUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Optional;

@Component

public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private OAuth2UserRepository oAuth2UserRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        String provider = defaultOAuth2User.getAttribute("provider");
        String providerUserId = defaultOAuth2User.getAttribute("id");
        String email = defaultOAuth2User.getAttribute("email");

//      조회해야함, 매퍼 만들기

    Optional<OAuth2User> optionalOAuth2User = oAuth2UserRepository.getOAuth2UserByProviderAndProviderUserId(provider, providerUserId);

    if (optionalOAuth2User.isEmpty()){
        response.sendRedirect("http://localhost:3000/auth/oauth2?provider=" + provider +"&providerUserId=" + providerUserId + "&email=" + email);
        return;
    }

    OAuth2User oAuth2User = optionalOAuth2User.get();

    Optional<User> optionalUser = userRepository.getUserByUserId(oAuth2User.getUserId());


    String accessToken = null;
    if (optionalUser.isPresent()) {
        accessToken = jwtUtils.generateAccessToken(optionalUser.get().getUserId().toString());
    }

    response.sendRedirect("http://localhost:3000/auth/oauth2/signin?accessToken=" + accessToken);

    }
}
