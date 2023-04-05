package com.co.companion.persistence;

import com.co.companion.model.SBoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Repository
public interface SBoardRepository extends JpaRepository<SBoardEntity, String> {

    @Query(value = "select id, user_id, type, title, ctt, img \n" +
            "\t\t, sigungu, reg_time \n" +
            "from sboard \n" +
            "where UPPER(title) like UPPER(CONCAT('%',:keyword,'%')) and type like %:type% \n" +
            "and case when coalesce(:start, '') != '' then (date between :start and :end) else '1' end \n" +
            "and sido like %:sido% and sigungu like %:sigungu% \n" +
            "order by reg_time desc\n" +
            "limit 10\n" +
            "offset (:page - 1) * 10",
            nativeQuery = true)
    List<Map> findPageList(@Param("keyword") String keyword, @Param("page") int page, @Param("type") String type
            , @Param("start") String start, @Param("end") String end, @Param("sido") String sido, @Param("sigungu") String sigungu);

}
