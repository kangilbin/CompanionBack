package com.co.companion.dto;

import com.co.companion.model.SBoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SBoardDTO {
    private String id;
    private String user_id;
    private String title;
    private String ctt;
    private String img;
    private String type;
    private String sido;
    private String sigungu;
    private String dong;
    private String addr;
    private String date;
    private String latitude;
    private String longitude;

    public SBoardDTO(final SBoardEntity entity) {
        this.id = entity.getId();
        this.user_id = entity.getUser_id();
        this.title = entity.getTitle();
        this.ctt = entity.getCtt();
        this.img = entity.getImg();
        this.type = entity.getType();
        this.sido = entity.getSido();
        this.sigungu = entity.getSigungu();
        this.dong = entity.getDong();
        this.addr = entity.getAddr();
        this.date = entity.getDate();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
    }

    public static SBoardEntity toEntity(final SBoardDTO dto) {
        return SBoardEntity.builder()
                .id(dto.getId())
                .user_id(dto.getUser_id())
                .title(dto.getTitle())
                .ctt(dto.getCtt())
                .type(dto.getType())
                .img(dto.getImg())
                .sido(dto.getSido())
                .sigungu(dto.getSigungu())
                .dong(dto.getDong())
                .addr(dto.getAddr())
                .date(dto.getDate())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
    }
}
