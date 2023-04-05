package com.co.companion.service;

import com.co.companion.model.BoardEntity;
import com.co.companion.model.SBoardEntity;
import com.co.companion.persistence.BoardRepository;
import com.co.companion.persistence.SBoardRepository;
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
public class SBoardService {

    @Autowired
    private SBoardRepository repository;

    // 찾습니다 게시판 insert
    public void create(final SBoardEntity entity) {
        validate(entity);
        repository.save(entity);
    }

    // 게시글 select
    public  Optional<SBoardEntity> boardSelect(final String id) {
        return repository.findById(id);
    }

    // 페이징
    public List<Map> paging (final String keyword, final int page, final String type, final String start
            , final String end, final String sido, final String sigungu) {

        return  repository.findPageList(keyword, page, type, start, end, sido, sigungu);
    }

    // 검증
    private void validate(final SBoardEntity entity) {
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
