package com.co.companion.dto;

import com.co.companion.model.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private String id;
    private String user_id;
    private String title;
    private String content;
    private String img;
    private String dom;
    private int hits;
    private int likes;

    public BoardDTO(final BoardEntity entity) {
        this.id = entity.getId();
        this.user_id = entity.getUser_id();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.img = entity.getImg();
        this.dom = entity.getDom();
        this.hits = entity.getHits();
        this.likes = entity.getLikes();
    }

    public static BoardEntity toEntity(final BoardDTO dto) {
        return BoardEntity.builder()
                .id(dto.getId())
                .user_id(dto.getUser_id())
                .title(dto.getTitle())
                .content(dto.getContent())
                .dom(dto.getDom())
                .img(dto.getImg())
                .hits(dto.getHits())
                .likes(dto.getLikes())
                .build();
    }
}
