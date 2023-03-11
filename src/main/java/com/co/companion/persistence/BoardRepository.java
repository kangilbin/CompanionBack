package com.co.companion.persistence;

import com.co.companion.model.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, String> {

    @Query(value = "select id, user_id, title, content, img, likes, hits\n" +
            "\t\t, (select COUNT(*) from comment where board_id = id) as comments, reg_time \n" +
            "from board \n" +
            "where title like %?1%",
            countQuery = "select count(*) from board",
            nativeQuery = true)
    Page<Map> findPageList(String keyword, Pageable page);
}
