package com.korIt.BoardStudy.sercurity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.korIt.BoardStudy.entity.UserRole;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder

public class PrincipalUser implements UserDetails {

    private Integer userId;
    private String username;
    @JsonIgnore // 비번 안나오게.
    private String password;
    private String email;
    private List<UserRole> userRoles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream().map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getRoleName()))
                .collect(Collectors.toList());  // getAuth하면, 권한리스트를 출력할 것임
    }
}
