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
@Table(name = "SBOARD")
public class SBoardEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "user_id")
    private String user_id;

    @Column(name = "title")
    private String title;

    @Column(name = "ctt")
    private String ctt;

    @Column(name = "type")
    private String type;

    @Column(name = "img")
    private String img;

    @Column(name = "sido")
    private String sido;

    @Column(name = "sigungu")
    private String sigungu;

    @Column(name = "dong")
    private String dong;

    @Column(name = "addr")
    private String addr;

    @Column(name = "date")
    private String date;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "reg_time")
    private Date reg_time;

    @Column(name = "mod_time")
    private Date mod_time;
}
