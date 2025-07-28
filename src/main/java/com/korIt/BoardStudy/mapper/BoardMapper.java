package com.korIt.BoardStudy.mapper;

import com.korIt.BoardStudy.entity.Board;
import org.apache.ibatis.annotations.Mapper;

@Mapper

public interface BoardMapper {
    int addBoard(Board board);

}
