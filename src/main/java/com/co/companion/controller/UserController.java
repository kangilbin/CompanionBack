package com.co.companion.controller;

import com.co.companion.dto.ResponseDTO;
import com.co.companion.dto.UserDTO;
import com.co.companion.model.UserEntity;
import com.co.companion.security.TokenProvider;
import com.co.companion.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("auth")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("login")
    public ResponseEntity<?> userSelect(@RequestBody UserDTO userDTO) {
        Optional<UserEntity> user = service.getByCredentials(userDTO.getId(), userDTO.getPassword(), passwordEncoder);

        if(user != null) {
            // 토큰 생성
            final String token = tokenProvider.create(user.get());
            final UserDTO responseUserDTO = UserDTO.builder()
                    .id(user.get().getId())
                    .user_name(user.get().getUser_name())
                    .nickname(user.get().getNickname())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("암호를 확인해 주세요.")
                    .success(false)
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/join")
    public ResponseEntity<?> userInsert(@RequestBody UserDTO dto) {
        try {
            // 요청을 이용해 저장할 사용자 만들기
            UserEntity user = UserEntity.builder()
                    .id(dto.getId())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .user_name(dto.getUser_name())
                    .phone(dto.getPhone())
                    .nickname(dto.getNickname())
                    .addr(dto.getAddr())
                    .addr_detail(dto.getAddr_detail())
                    .zip_no(dto.getZip_no())
                    .build();

            // 서비스를 이용해 리포지터리에 사용자 저장
            UserEntity registeredUser = service.create(user);
            UserDTO responseUserDTO = UserDTO.builder()
                    .id(registeredUser.getId())
                    .user_name(registeredUser.getUser_name())
                    .nickname(registeredUser.getNickname())
                    .build();

            return ResponseEntity.ok().body(responseUserDTO);
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
    @GetMapping("/check")
    public ResponseEntity<?> nickDuplicateCheck(@RequestParam(required = false) String nickname) {
        return ResponseEntity.ok().body(service.nickDupCheck(nickname));
    }
}
