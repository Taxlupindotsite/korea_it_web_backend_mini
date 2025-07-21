package com.korIt.BoardStudy.repository;

import com.korIt.BoardStudy.entity.UserRole;
import com.korIt.BoardStudy.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository

public class UserRoleRepository {

    @Autowired
    private UserRoleMapper userRoleMapper;

    public int addUserRole(UserRole userRole) {
        return userRoleMapper.addUserRole(userRole);
            }
}
