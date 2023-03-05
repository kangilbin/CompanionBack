package com.co.companion.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "BOARD")
public class BoardEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "user_id")
    private String user_id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "dom")
    private String dom;

    @Column(name = "img")
    private String img;

    @Column(name = "likes")
    private int likes;

    @Column(name = "hits")
    private int hits;

    @Column(name = "reg_time")
    private Date reg_time;

    @Column(name = "mod_time")
    private Date mod_time;
}
