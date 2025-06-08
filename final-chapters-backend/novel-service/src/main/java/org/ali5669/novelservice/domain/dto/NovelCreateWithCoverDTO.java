package org.ali5669.novelservice.domain.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class NovelCreateWithCoverDTO {
    private String authorId;
    private String title;
    private String summary;
    private String category;
    private String tags;
    private MultipartFile cover;
}
