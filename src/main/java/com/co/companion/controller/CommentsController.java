package com.co.companion.controller;


import com.co.companion.dto.CommentDTO;
import com.co.companion.dto.ResponseDTO;
import com.co.companion.model.BoardEntity;
import com.co.companion.model.CommentEntity;
import com.co.companion.service.BoardService;
import com.co.companion.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("comment")
public class CommentsController {

    @Autowired
    private CommentService service;
    @GetMapping("/{id}")
    public ResponseEntity<?> communityComments(@PathVariable(required = false) String id){
        List<Map> board = service.commentsSelect(id);
        return ResponseEntity.ok().body(board);
    }
    @PostMapping("/new")
    public ResponseEntity<?> commentsInsert(@RequestBody CommentDTO dto){
        try {

            // 추후 스프링 시큐리티 사용하여 아이디 넣을 예정
            dto.setUser_id("jacob");
            List<Map> board = service.commentCreate(dto);
        return ResponseEntity.ok().body(board);
        } catch (Exception e) {
            // (8) ,혹시 예외가 있는 경우 dto 대신 error에 메시지를 넣어 리턴한다.
            String error = e.getMessage();
            ResponseDTO<CommentDTO> response = ResponseDTO.<CommentDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/reply")
    public ResponseEntity<?> commentsReInsert(@RequestBody CommentDTO dto){
        try {

            // 추후 스프링 시큐리티 사용하여 아이디 넣을 예정
            dto.setUser_id("jacob");
            List<Map> board = service.commentReCreate(dto);
            return ResponseEntity.ok().body(board);
        } catch (Exception e) {
            // (8) ,혹시 예외가 있는 경우 dto 대신 error에 메시지를 넣어 리턴한다.
            String error = e.getMessage();
            ResponseDTO<CommentDTO> response = ResponseDTO.<CommentDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
