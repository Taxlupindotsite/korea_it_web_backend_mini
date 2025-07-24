package com.korIt.BoardStudy.dto.oauth2;

import com.korIt.BoardStudy.entity.OAuth2User;
import lombok.Data;

@Data

public class OAuth2MergeReqDto {
    private String username;
    private String password;
    private String provider;
    private String providerUserId;

    public OAuth2User toOAuth2User(Integer userId) {
        return OAuth2User.builder()
                .userId(userId)
                .providerUserId(providerUserId)
                .build();
    }





}
