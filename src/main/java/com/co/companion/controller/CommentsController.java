package com.co.companion.controller;


import com.co.companion.dto.CommentDTO;
import com.co.companion.dto.ResponseDTO;
import com.co.companion.dto.UserDTO;
import com.co.companion.security.CustomUserDetails;
import com.co.companion.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> commentsInsert(@AuthenticationPrincipal CustomUserDetails user, @RequestBody CommentDTO dto){
        try {
            dto.setUser_id(user.getNickname());
            List<Map> comments = service.commentCreate(dto);
            return ResponseEntity.ok().body(comments);
        } catch (HttpClientErrorException e) {
            String error = e.getMessage();
            ResponseDTO<CommentDTO> response = ResponseDTO.<CommentDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/reply")
    public ResponseEntity<?> commentsReInsert(@AuthenticationPrincipal CustomUserDetails user, @RequestBody CommentDTO dto){
        try {
            dto.setUser_id(user.getNickname());
            List<Map> comments = service.commentReCreate(dto);
            return ResponseEntity.ok().body(comments);
        } catch (HttpClientErrorException e) {
            String error = e.getMessage();
            ResponseDTO<CommentDTO> response = ResponseDTO.<CommentDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
