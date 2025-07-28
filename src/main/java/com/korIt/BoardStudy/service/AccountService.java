package com.korIt.BoardStudy.service;

import com.korIt.BoardStudy.dto.ApiRespDto;
import com.korIt.BoardStudy.dto.account.ChangePasswordReqDto;
import com.korIt.BoardStudy.entity.User;
import com.korIt.BoardStudy.repository.UserRepository;
import com.korIt.BoardStudy.sercurity.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    public ApiRespDto<?> changePassword(ChangePasswordReqDto changePasswordReqDto, PrincipalUser principalUser) {
        Optional<User> userByUserId = userRepository.getUserByUserId(principalUser.getUserId());
        if (userByUserId.isEmpty()) {
            return new ApiRespDto<>("failed", "존재하지 않는 사용자입니다.", null);

        }

        if (!Objects.equals(changePasswordReqDto.getUserId(), principalUser.getUserId())){
            return new ApiRespDto<>("failed", "잘못된 요청입니다.", null);
        }

        if (!bCryptPasswordEncoder.matches(changePasswordReqDto.getOldPassword(), userByUserId.get().getPassword())) {
            return new ApiRespDto<>("failed", "기존 비밀번호가 일치하지 않습니다.", null);

        }

        if (bCryptPasswordEncoder.matches(changePasswordReqDto.getNewPassword(), userByUserId.get().getPassword())) {
            return new ApiRespDto<>("failed","새 비밀번호가 기존과 일치합니다.", null);
        }

        int result = userRepository.changePassword(changePasswordReqDto.toEntity(bCryptPasswordEncoder));
        if (result !=1) {
            return new ApiRespDto<>("failed", "문제가 발생했습니다.", null);

        }

        return new ApiRespDto<>("success", "비밀번호 변경이 완료되었습니다. \n다시 로그인 해주세요." , null);
    }

}
