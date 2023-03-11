package com.co.companion.service;


import com.co.companion.model.BoardEntity;
import com.co.companion.persistence.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.Optional;

@WebMvcTest(BoardService.class)
public class BoardServiceTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void boardSelect() {
        Optional<BoardEntity> board = boardRepository.findById("20230306122235");

        System.out.println(board.toString());
    }
}
