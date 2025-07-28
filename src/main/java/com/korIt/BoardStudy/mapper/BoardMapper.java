package com.korIt.BoardStudy.mapper;

import com.korIt.BoardStudy.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper

public interface BoardMapper {
    int addBoard(Board board);

    Optional<Board> getBoardByBoardId(Integer boardId);


}
