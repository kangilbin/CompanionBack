package com.co.companion.service;

import com.co.companion.model.BoardEntity;
import com.co.companion.persistence.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    // 커뮤니티 게시판 insert
    public void create(final BoardEntity entity) {
        validate(entity);
        boardRepository.save(entity);
    }

    public List<Map> paging (final int page) {
        return boardRepository.findPageList(page);
    }

    // 검증
    private void validate(final BoardEntity entity) {
        if(entity == null) {
            log.warn("Entity 없음.");
            throw new RuntimeException("Entity 없음");
        }

        if(entity.getUser_id() == null) {
            log.warn("작성자 이름 null");
            throw new RuntimeException("작성자 이름 null");
        }
    }
}
