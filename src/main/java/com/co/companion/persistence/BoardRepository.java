package com.co.companion.persistence;

import com.co.companion.model.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BoardRepository  extends JpaRepository<BoardEntity, String> {

    @Query(value = "select id, user_id, title, content, img, likes, hits\n" +
            "\t\t, (select COUNT(*) from comment where board_id = ID) as comments, reg_time \n" +
            "from board \n" +
            "order by reg_time desc\n" +
            "limit 10\n" +
            "offset (?1 - 1) * 10\n", nativeQuery = true)
    List<Map> findPageList(int page);
}
