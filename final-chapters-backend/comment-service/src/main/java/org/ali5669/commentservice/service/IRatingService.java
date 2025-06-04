package org.ali5669.commentservice.service;

import org.ali5669.commentservice.domain.dto.RatingCountDTO;
import org.ali5669.commentservice.domain.dto.Result;
import org.ali5669.commentservice.domain.po.Rating;

import java.util.List;

public interface IRatingService {
    Result createRating(Rating rating);

    Double getAverageRating(int novelId);

    List<RatingCountDTO> listByNovelId(int i);
}
