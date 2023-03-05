package com.co.companion.controller;


import com.co.companion.dto.BoardDTO;
import com.co.companion.dto.ResponseDTO;
import com.co.companion.model.BoardEntity;
import com.co.companion.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;


@Slf4j
@RestController
@RequestMapping("board")
public class BoardController {

    @Autowired
    private BoardService service;

    @PostMapping("community/write")
    public ResponseEntity<?> community(@RequestBody BoardDTO dto) {
        try {
            log.info(dto.toString());
            BoardEntity entity = BoardDTO.toEntity(dto);
            service.create(entity);

            return ResponseEntity.ok().body(true);
        } catch (HttpClientErrorException e) {
            log.error("커뮤니티 글쓰기 실패");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("community/list")
    public ResponseEntity<?> community(@RequestParam(required = false) int page) {
        try {
            log.info("page : " + page);
            return ResponseEntity.ok().body(service.paging(page));
        } catch (HttpClientErrorException e) {
            log.error("커뮤니티 글쓰기 실패");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
