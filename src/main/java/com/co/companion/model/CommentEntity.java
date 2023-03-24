package com.co.companion.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "comment")
public class CommentEntity {
    @Id
    @Column(name = "board_id")
    private String board_id;

    @Column(name = "parent_comment_id")
    private int parent_comment_id;

    @Column(name = "comment_id")
    private int comment_id;

    @Column(name = "content")
    private String content;

    @Column(name = "user_id")
    private String user_id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "del_yn")
    private char del_yn;

    @Column(name = "reg_time")
    private Date reg_time;

    @Column(name = "mod_time")
    private Date mod_time;

    @Column(name = "del_time")
    private Date del_time;
}

