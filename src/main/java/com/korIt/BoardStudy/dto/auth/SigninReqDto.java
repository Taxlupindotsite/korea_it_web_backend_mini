package com.korIt.BoardStudy.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class SigninReqDto {

    private String username;
    private String password;



}
