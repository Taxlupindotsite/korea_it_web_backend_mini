package com.korIt.BoardStudy.mapper;

import com.korIt.BoardStudy.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper

public interface UserMapper {
    Optional<User> getUserByUserId(Integer userId);



}
