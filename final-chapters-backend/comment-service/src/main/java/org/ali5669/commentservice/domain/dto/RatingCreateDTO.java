package org.ali5669.commentservice.domain.dto;

import lombok.Data;

@Data
public class RatingCreateDTO {
    private String userId;
    private String novelId;
    private String rating;
}

