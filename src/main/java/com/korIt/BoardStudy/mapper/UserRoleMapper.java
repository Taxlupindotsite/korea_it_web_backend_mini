package com.korIt.BoardStudy.mapper;

import com.korIt.BoardStudy.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper

public interface UserRoleMapper {

    int addUserRole(UserRole userRole);
}
