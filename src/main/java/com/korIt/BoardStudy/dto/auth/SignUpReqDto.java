package com.korIt.BoardStudy.dto.auth;

import com.korIt.BoardStudy.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@AllArgsConstructor

public class SignUpReqDto {

    private String username;
    private String password;
    private String email;

    public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return User.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .email(email)
                .build();
    }


}
