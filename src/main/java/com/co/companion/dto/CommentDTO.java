package com.co.companion.dto;

import com.co.companion.model.BoardEntity;
import com.co.companion.model.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private String board_id;
    private int parent_comment_id;
    private int comment_id;
    private String content;
    private String user_id;
    private Date mod_time;
    private Date del_time;

    public CommentDTO(final CommentEntity entity) {
        this.board_id = entity.getBoard_id();
        this.parent_comment_id = entity.getParent_comment_id();
        this.comment_id = entity.getComment_id();
        this.content = entity.getContent();
        this.user_id = entity.getUser_id();
        this.mod_time = entity.getMod_time();
        this.del_time = entity.getDel_time();
    }

    public static CommentEntity toEntity(final CommentDTO dto) {
        return CommentEntity.builder()
                .board_id(dto.getBoard_id())
                .parent_comment_id(dto.getParent_comment_id())
                .comment_id(dto.getComment_id())
                .content(dto.getContent())
                .user_id(dto.getUser_id())
                .mod_time(dto.getMod_time())
                .del_time(dto.getDel_time())
                .build();
    }
}
