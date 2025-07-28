package com.korIt.BoardStudy.repository;

import com.korIt.BoardStudy.entity.Board;
import com.korIt.BoardStudy.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository

public class BoardRepository {

    @Autowired
    private BoardMapper boardMapper;

    public int addBoard(Board board) {
        return boardMapper.addBoard(board);

    }

}
