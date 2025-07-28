package com.korIt.BoardStudy.controller;

import com.korIt.BoardStudy.dto.board.AddBoardReqDto;
import com.korIt.BoardStudy.sercurity.model.PrincipalUser;
import com.korIt.BoardStudy.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")

public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping("/add")
    public ResponseEntity<?> addBoard(@RequestBody AddBoardReqDto addBoardReqDto, @AuthenticationPrincipal PrincipalUser principalUser){
        return ResponseEntity.ok(boardService.addBoard(addBoardReqDto, principalUser));
    }




}
