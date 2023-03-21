package com.co.companion.controller;


import com.co.companion.dto.BoardDTO;
import com.co.companion.model.BoardEntity;
import com.co.companion.security.CustomUserDetails;
import com.co.companion.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private BoardService service;

    @PostMapping("community/write")
    public ResponseEntity<?> communityWrite(@AuthenticationPrincipal CustomUserDetails user, @RequestBody BoardDTO dto) {
        try {
            dto.setUser_id(user.getNickname());
            BoardEntity entity = BoardDTO.toEntity(dto);
            service.create(entity);

            return ResponseEntity.ok().body(true);
        } catch (HttpClientErrorException e) {
            log.error("커뮤니티 글쓰기 실패");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("community/list")
    public ResponseEntity<?> communityList(@RequestParam(required = false) int page, @RequestParam(required = false) String keyword, @RequestParam(required = false) String sort) {
        return ResponseEntity.ok().body(service.paging(page, keyword, sort));
    }

    @GetMapping("community/read/{id}")
    public ResponseEntity<?> communityRead(@PathVariable(required = false) String id){
        Optional<BoardEntity> board = service.boardSelect(id);
        return ResponseEntity.ok().body(board);
    }


}
