package org.ali5669.novelservice.domain.dto;

import lombok.Data;

@Data
public class ChapterCreateDTO {
    private String novelId;
    private String title;
    private String content;
    private String order;
}
