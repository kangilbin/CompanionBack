package com.co.companion.dto;

import com.co.companion.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String password;
    private String user_name;
    private String nickname;
    private int phone;
    private String addr;
    private String addr_detail;
    private String zip_no;
    private Date mod_time;
    private Date del_time;
    private String token;

    public UserDTO(final UserEntity entity) {
        this.id = entity.getId();
        this.password = entity.getPassword();
        this.user_name = entity.getUser_name();
        this.phone = entity.getPhone();
        this.nickname = entity.getNickname();
        this.addr = entity.getAddr();
        this.addr_detail = entity.getAddr_detail();
        this.zip_no = entity.getZip_no();
        this.mod_time = entity.getMod_time();
        this.del_time = entity.getDel_time();
    }

    public static UserEntity toEntity(final UserDTO dto) {
        return UserEntity.builder()
                .id(dto.getId())
                .password(dto.getPassword())
                .user_name(dto.getUser_name())
                .nickname(dto.getNickname())
                .phone(dto.getPhone())
                .addr(dto.getAddr())
                .addr_detail(dto.getAddr_detail())
                .zip_no(dto.getZip_no())
                .mod_time(dto.getMod_time())
                .del_time(dto.getDel_time())
                .build();
    }
}
