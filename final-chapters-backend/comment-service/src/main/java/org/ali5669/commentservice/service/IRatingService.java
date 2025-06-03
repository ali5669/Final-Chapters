package org.ali5669.commentservice.service;

import org.ali5669.commentservice.domain.dto.Result;
import org.ali5669.commentservice.domain.po.Rating;

public interface IRatingService {
    Result createRating(Rating rating);

    Double getAverageRating(int novelId);

    Object listByNovelId(int i);
}
