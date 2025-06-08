package org.ali5669.commentservice.domain.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private String novelId;
    private String chapterId;
    private String content;
}
