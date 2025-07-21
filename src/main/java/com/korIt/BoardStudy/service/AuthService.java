package com.korIt.BoardStudy.service;

import com.korIt.BoardStudy.dto.ApiRespDto;
import com.korIt.BoardStudy.dto.auth.SignUpReqDto;
import com.korIt.BoardStudy.entity.User;
import com.korIt.BoardStudy.entity.UserRole;
import com.korIt.BoardStudy.repository.UserRepository;
import com.korIt.BoardStudy.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service


public class AuthService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)

    @Autowired
    private UserRoleRepository userRoleRepository;

    public ApiRespDto<?> signup(SignUpReqDto signUpReqDto) {
//    아이디 중복확인
        Optional<User> userByUsername = userRepository.getUserByUsername(signUpReqDto.getUsername());
        if (userByUsername.isPresent()) {
            return new ApiRespDto<>("Failed", "이미 사용중인 아이디입니다.", null);
        }
//   이메일 중복확인
        Optional<User> userByEmail = userRepository.getUserByEmail(signUpReqDto.getEmail());
        if (userByEmail.isPresent()) {
            return new ApiRespDto<>("failed", "이미 사용중인 이메일입니다.", null);
        }

        try {
//            사용자 정보 추가.
            Optional<User> optionalUser = userRepository.addUser(signUpReqDto.toEntity(bCryptPasswordEncoder));

            if (optionalUser.isEmpty()) {
                throw new RuntimeException("회원정보 추가에 실패함");
            }

        User user = optionalUser.get();

            UserRole userRole = UserRole.builder()
                    .userId(user.getUserId())
                    .roleId(3)
                    .build();

            int addUserRoleResult = userRoleRepository.addUserRole(userRole);
            if (addUserRoleResult != 1) {
                throw new RuntimeException("권한 추가에 실패했습니다.");
            }

            return new ApiRespDto<>("success", "회원가입 성공", user);

    } catch (Exception e){
            return new ApiRespDto<>("failed", "회원가입 오류 발생 : " + e.getMessage(), null);
        }


    }

}
