package com.korIt.BoardStudy.repository;

import com.korIt.BoardStudy.entity.UserRole;
import com.korIt.BoardStudy.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public class UserRoleRepository {

    @Autowired
    private UserRoleMapper userRoleMapper;

    public int addUserRole(UserRole userRole) {
        return userRoleMapper.addUserRole(userRole);
    }

    public Optional<UserRole> getUserRoleByUserIdAndRoleId(Integer userId, Integer roleId) {
        return userRoleMapper.getUserRoleByUserIdAndRoleId(userId, roleId);
    }

    public int updateRoleId(Integer userRoleId, Integer userId) {
        return userRoleMapper.updateRoleId(userRoleId, userId);
    }

}
