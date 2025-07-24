package com.korIt.BoardStudy.service;


import com.korIt.BoardStudy.dto.ApiRespDto;
import com.korIt.BoardStudy.dto.oauth2.OAuth2MergeReqDto;
import com.korIt.BoardStudy.entity.OAuth2User;
import com.korIt.BoardStudy.entity.User;
import com.korIt.BoardStudy.repository.OAuth2UserRepository;
import com.korIt.BoardStudy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service

public class OAuth2AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OAuth2UserRepository oAuth2UserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(rollbackFor = Exception.class)

    public ApiRespDto<?> mergeAccount(OAuth2MergeReqDto oAuth2MergeReqDto) {
        Optional<User> optionalUser = userRepository.getUserByUsername(oAuth2MergeReqDto.getUsername());
        if (optionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "사용자 정보를 확인하십쇼", null);

        }

        User user = optionalUser.get();

        Optional<OAuth2User> optionalOAuth2User =
                oAuth2UserRepository.getOAuth2UserByProviderAndProviderUserId(oAuth2MergeReqDto.getProvider(), oAuth2MergeReqDto.getProviderUserId());
        if (optionalOAuth2User.isPresent()) {
            return new ApiRespDto<>("failed", "이 계정은 이미 소셜 계정과 연동되어 있습니다.", null);
        }

        if (!bCryptPasswordEncoder.matches(oAuth2MergeReqDto.getPassword(), user.getPassword())) {
            return new ApiRespDto<>("failed", "사용자 정보를 확인하세요.", null);
        }

        try {
            int result = oAuth2UserRepository.addOAuth2User(oAuth2MergeReqDto.toOAuth2User(user.getUserId()));
            if (result != 1) {
                throw new RuntimeException("OAuth2 사용자 정보 연동에 실패했습니다.");
            }

            return new ApiRespDto<>("success", "계정연동 성공", null);
        } catch (Exception e) {
            return new ApiRespDto<>("failed", "계정 연동 중 오류가 발생했습니다 :" + e.getMessage(),null);
        }
    }
}



