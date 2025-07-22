package com.korIt.BoardStudy.repository;

import com.korIt.BoardStudy.entity.OAuth2User;
import com.korIt.BoardStudy.mapper.Oauth2UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public class OAuth2UserRepository {

    @Autowired
    private Oauth2UserMapper oauth2UserMapper;

    public Optional<OAuth2User> getOAuth2UserByProviderAndProviderUserId(String provider, String providerUserId) {
        return oauth2UserMapper.getOAuth2UserByProviderAndProviderUserId(provider, providerUserId);

    }

}
