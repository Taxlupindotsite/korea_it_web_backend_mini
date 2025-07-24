package com.korIt.BoardStudy.dto.oauth2;

import com.korIt.BoardStudy.entity.OAuth2User;
import com.korIt.BoardStudy.entity.User;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data

public class OAuth2SignupReqDto {
    private String username;
    private String password;
    private String email;
    private String provider;
    private String providerUserId;

    public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return User.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .email(email)
                .build();
    }

    public OAuth2User toOAuth2User(Integer userId) {
        return OAuth2User.builder()
                .userId(userId)
                .provider(provider)
                .providerUserId(providerUserId)
                .build();

    }

}
