package com.korIt.BoardStudy.service;

import com.korIt.BoardStudy.dto.ApiRespDto;
import com.korIt.BoardStudy.dto.board.AddBoardReqDto;
import com.korIt.BoardStudy.repository.BoardRepository;
import com.korIt.BoardStudy.sercurity.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Transactional(rollbackFor = Exception.class)

    public ApiRespDto<?> addBoard(AddBoardReqDto addBoardReqDto, PrincipalUser principalUser) {
        // principalUser 객체 자체가 아닌, 객체 안의 userId와 비교하도록 수정
        // 🟢 수정된 코드
        if (principalUser == null || !addBoardReqDto.getUserId().equals(principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 접근입니다. 로그인 정보가 유효하지 않거나 권한이 없습니다.", null);
        }

        if (addBoardReqDto.getTitle() == null || addBoardReqDto.getTitle().trim().isEmpty()) {
            return new ApiRespDto<>("failed", "제목은 필수 입력사항입니다.", null);

        }

        if (addBoardReqDto.getContent() == null || addBoardReqDto.getContent().trim().isEmpty()) {
            return new ApiRespDto<>("failed", "내용은 필수 입력사항입니다.", null);

        }
        try {
            int result = boardRepository.addBoard(addBoardReqDto.toEntity());
            if (result != 1) {
                return new ApiRespDto<>("failed", "게시물 추가에 실패했습니다.", null);
            }

            return new ApiRespDto<>("success", "게시물이 성공적으로 추가되었습니다.", null);
        } catch (Exception e) {
            return new ApiRespDto<>("failed", "서버 오류로 게시물 추가에 실패했습니다: " + e.getMessage(), null);


        }
    }
}

