package com.korIt.BoardStudy.service;

import com.korIt.BoardStudy.dto.ApiRespDto;
import com.korIt.BoardStudy.dto.auth.SignUpReqDto;
import com.korIt.BoardStudy.dto.auth.SigninReqDto;
import com.korIt.BoardStudy.entity.User;
import com.korIt.BoardStudy.entity.UserRole;
import com.korIt.BoardStudy.repository.UserRepository;
import com.korIt.BoardStudy.repository.UserRoleRepository;
import com.korIt.BoardStudy.sercurity.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service


public class AuthService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Transactional(rollbackFor = Exception.class)

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

//    2:30 이후
    public ApiRespDto<?> signin(SigninReqDto signinReqDto) {
        Optional<User> optionalUser = userRepository.getUserByUsername(signinReqDto.getUsername());
        if (optionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "계정 정보를 확인해주세요.", null);
        }

        User user = optionalUser.get();

        if (!bCryptPasswordEncoder.matches(signinReqDto.getPassword(), user.getPassword())) {
            return new ApiRespDto<>("failed", "게정 정보를 확인하세요.", null);
        }

        String accessToken = jwtUtils.generateAccessToken(user.getUserId().toString());
        return new ApiRespDto<>("success", "로그인 성공", accessToken);

    }

}
