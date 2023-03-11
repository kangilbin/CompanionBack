package com.co.companion.service;

import com.co.companion.dto.CommentDTO;
import com.co.companion.model.BoardEntity;
import com.co.companion.model.CommentEntity;
import com.co.companion.persistence.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;



    // 댓글 select
    public List<Map> commentsSelect(final String id) {
        return repository.findByBoardId(id);
    }

    // 댓글 insert
    public List<Map> commentCreate(final CommentDTO dto) {
        repository.insert(dto);
        return repository.findByBoardId(dto.getBoard_id());
    }

    // 답글 insert
    public List<Map> commentReCreate(final CommentDTO dto) {
        repository.replyInsert(dto);
        return repository.findByBoardId(dto.getBoard_id());
    }

    // 검증
    private void validate(final CommentEntity entity) {
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
