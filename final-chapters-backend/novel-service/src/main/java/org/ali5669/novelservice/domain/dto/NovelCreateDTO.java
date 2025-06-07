package org.ali5669.novelservice.domain.dto;

import lombok.Data;

@Data
public class NovelCreateDTO {
    private String authorId;
    private String title;
    private String summary;
    private String category;
    private String tags;
}
