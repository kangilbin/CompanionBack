package com.co.companion.service;

import com.co.companion.dto.BoardDTO;
import com.co.companion.model.BoardEntity;
import com.co.companion.persistence.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    // 게시글 select
    public  Optional<BoardEntity> boardSelect(final String id) {
        boardRepository.likesIncrease(id);

        return boardRepository.findById(id);
    }




    // 페이징
    public Page<Map> paging (final int page, final String keyword, final String sort) {
        Pageable pageable = PageRequest.of(page,10,  Sort.Direction.DESC, sort);

        return  boardRepository.findPageList(keyword, pageable);
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
