package com.korIt.BoardStudy.mapper;

import com.korIt.BoardStudy.entity.OAuth2User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper

public interface Oauth2UserMapper {
    Optional<OAuth2User> getOAuth2UserByProviderAndProviderUserId(String provider, String providerUserId);
    int addOAuth2User(OAuth2User oAuth2User);

}
