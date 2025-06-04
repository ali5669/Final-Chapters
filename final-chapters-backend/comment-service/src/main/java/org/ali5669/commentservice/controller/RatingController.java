package org.ali5669.commentservice.controller;

import lombok.RequiredArgsConstructor;
import org.ali5669.commentservice.domain.dto.RatingCreateDTO;
import org.ali5669.commentservice.domain.dto.Result;
import org.ali5669.commentservice.domain.po.Rating;
import org.ali5669.commentservice.service.IRatingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RatingController {
    private final IRatingService ratingService;

    @PostMapping
    public Result addRating(@RequestBody RatingCreateDTO createDTO){
        Integer userIdInt = Integer.parseInt(createDTO.getUserId());
        Integer novelIdInt = Integer.parseInt(createDTO.getNovelId());
        Integer ratingInt = Integer.parseInt(createDTO.getRating());

        Rating rating = new Rating();
        rating.setUserId(userIdInt);
        rating.setNovelId(novelIdInt);
        rating.setRating(ratingInt);

        return ratingService.createRating(rating);
    }

    @GetMapping("/average")
    public Result getAverageRating(@RequestParam("novelId") String novelId){
        return Result.ok(ratingService.getAverageRating(Integer.parseInt(novelId)).toString());
    }

    @GetMapping("/list")
    public Result listByNovelId(@RequestParam("novelId") String novelId){
        return Result.ok(ratingService.listByNovelId(Integer.parseInt(novelId)));
    }
}
