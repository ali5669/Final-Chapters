package org.ali5669.domain.dto;

import lombok.Data;

@Data
public class ChapterCreateDTO {
    private String novelId;
    private String title;
    private String content;
    private String order;
}
