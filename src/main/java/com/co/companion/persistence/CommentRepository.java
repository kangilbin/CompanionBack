package com.co.companion.persistence;

import com.co.companion.dto.CommentDTO;
import com.co.companion.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, String> {

    @Query(value = "select parent_comment_id, comment_id, user_id \n" +
            ",case when del_yn = 'Y' then '*** 삭제된 댓글입니다. ***' else content end as content \n" +
            ",coalesce(mod_time, reg_time) dt \n" +
            "from comment \n" +
            "where board_id =?1\n" +
            "order by parent_comment_id, comment_id"
            ,nativeQuery = true)
    List<Map> findByBoardId(@Param("boardId") String boardId);

    @Transactional
    @Modifying
    @Query(value = "insert into comment \n" +
            "(board_id, PARENT_COMMENT_ID, COMMENT_ID, content, user_id)\n" +
            "values\n" +
            "(:#{#dto.board_id}, (select count(parent_comment_id) from \n" +
            "(select parent_comment_id from comment where board_id=:#{#dto.board_id} group by parent_comment_id) A\n" +
            "), 0, :#{#dto.content},:#{#dto.user_id})"
            ,nativeQuery = true)
    int insert(@Param(value = "dto") CommentDTO dto);

    @Transactional
    @Modifying
    @Query(value = "insert into comment \n" +
            "(board_id, PARENT_COMMENT_ID, COMMENT_ID, content, user_id)\n" +
            "values\n" +
            "(:#{#dto.board_id}, :#{#dto.parent_comment_id}, (select count(*) from comment where board_id=:#{#dto.board_id} and PARENT_COMMENT_ID=:#{#dto.parent_comment_id})" +
            ",:#{#dto.content}, :#{#dto.user_id})"
            ,nativeQuery = true)
    int replyInsert(@Param(value = "dto") CommentDTO dto);
}
