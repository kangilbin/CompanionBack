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
@Table(name = "employer")
public class UserEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "password")
    private String password;

    @Column(name = "user_name")
    private String user_name;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phone")
    private int phone;

    @Column(name = "addr")
    private String addr;

    @Column(name = "addr_detail")
    private String addr_detail;

    @Column(name = "zip_no")
    private String zip_no;

    @Column(name = "reg_time")
    private Date reg_time;

    @Column(name = "mod_time")
    private Date mod_time;

    @Column(name = "del_time")
    private Date del_time;
}
