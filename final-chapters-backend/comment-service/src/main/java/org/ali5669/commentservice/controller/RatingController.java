package org.ali5669.commentservice.controller;

import lombok.RequiredArgsConstructor;
import org.ali5669.commentservice.domain.dto.RatingCreateDTO;
import org.ali5669.commentservice.domain.dto.Result;
import org.ali5669.commentservice.domain.po.Rating;
import org.ali5669.commentservice.service.IRatingService;
import org.ali5669.commentservice.utils.UserContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RatingController {
    private final IRatingService ratingService;

    @PostMapping
    public Result addRating(@RequestBody RatingCreateDTO createDTO){
        Integer userIdInt = null;
        Integer novelIdInt = null;
        Integer ratingInt = null;
        try {
            userIdInt = UserContext.getUserId();
            novelIdInt = Integer.parseInt(createDTO.getNovelId());
            ratingInt = Integer.parseInt(createDTO.getRating());
        } catch (NumberFormatException e) {
            return Result.fail("参数错误: " + e);
        }

        Rating rating = new Rating();
        rating.setUserId(userIdInt);
        rating.setNovelId(novelIdInt);
        rating.setRating(ratingInt);

        return ratingService.createRating(rating);
    }

    @GetMapping("/average/{novelId}")
    public Result getAverageRating(@PathVariable String novelId){
        return Result.ok(ratingService.getAverageRating(Integer.parseInt(novelId)).toString());
    }

    @GetMapping("/list/{novelId}")
    public Result listByNovelId(@PathVariable String novelId){
        return Result.ok(ratingService.listByNovelId(Integer.parseInt(novelId)));
    }
}
