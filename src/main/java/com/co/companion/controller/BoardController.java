package com.co.companion.controller;


import com.co.companion.dto.BoardDTO;
import com.co.companion.dto.CommentDTO;
import com.co.companion.dto.ResponseDTO;
import com.co.companion.dto.SBoardDTO;
import com.co.companion.model.BoardEntity;
import com.co.companion.model.SBoardEntity;
import com.co.companion.security.CustomUserDetails;
import com.co.companion.service.BoardService;
import com.co.companion.service.SBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("board")
public class BoardController {

    @Autowired
    private BoardService c_service;

    @Autowired
    private SBoardService l_service;
    @PostMapping("community/write")
    public ResponseEntity<?> communityWrite(@AuthenticationPrincipal CustomUserDetails user, @RequestBody BoardDTO dto) {
        try {
            dto.setUser_id(user.getNickname());
            BoardEntity entity = BoardDTO.toEntity(dto);
            c_service.create(entity);

            return ResponseEntity.ok().body(true);
        } catch (HttpClientErrorException e) {
            log.error("커뮤니티 글쓰기 실패");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("community/list")
    public ResponseEntity<?> communityList(@RequestParam(required = false) int page, @RequestParam(required = false) String keyword, @RequestParam(required = false) String sort) {
        return ResponseEntity.ok().body(c_service.paging(page, keyword, sort));
    }

    @GetMapping("community/read/{id}")
    public ResponseEntity<?> communityRead(@PathVariable(required = false) String id){
        try{
            Optional<BoardEntity> board = c_service.boardSelect(id);
            return ResponseEntity.ok().body(board);
        } catch (HttpClientErrorException e) {
            String error = e.getMessage();
            ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("lose/write")
    public ResponseEntity<?> loseWrite(@AuthenticationPrincipal CustomUserDetails user, @RequestBody SBoardDTO dto) {
        try {
            dto.setUser_id(user.getNickname());
            SBoardEntity entity = SBoardDTO.toEntity(dto);
            l_service.create(entity);

            return ResponseEntity.ok().body(true);
        } catch (HttpClientErrorException e) {
            log.error("찾습니다 게시판 글쓰기 실패");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("lose/list")
    public ResponseEntity<?> loseList(@RequestParam(required = false) int page, @RequestParam(required = false) String keyword
            , @RequestParam(required = false) String type , @RequestParam(required = false) String start , @RequestParam(required = false) String end
            , @RequestParam(required = false) String sido, @RequestParam(required = false) String sigungu) {

        return ResponseEntity.ok().body(l_service.paging(keyword, page, type, start, end, sido, sigungu));
    }

    @GetMapping("lose/read/{id}")
    public ResponseEntity<?> loseRead(@PathVariable(required = false) String id){
        try{
            Optional<SBoardEntity> board = l_service.boardSelect(id);
            return ResponseEntity.ok().body(board);
        } catch (HttpClientErrorException e) {
            String error = e.getMessage();
            ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
