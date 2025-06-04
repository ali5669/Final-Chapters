package org.ali5669.commentservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.ali5669.commentservice.domain.dto.RatingCountDTO;
import org.ali5669.commentservice.domain.dto.Result;
import org.ali5669.commentservice.domain.po.Rating;
import org.ali5669.commentservice.mapper.RatingMapper;
import org.ali5669.commentservice.service.IRatingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RatingServiceImpl extends ServiceImpl<RatingMapper, Rating> implements IRatingService {
    @Override
    public Result createRating(Rating rating) {
        if(save(rating)){
            return Result.ok();
        }
        return Result.fail("create rating failed");
    }

    @Override
    public Double getAverageRating(int novelId) {
        QueryWrapper<Rating> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("novel_id", novelId);
        queryWrapper.select("avg(rating) as avg_rating");
        Map<String, Object> map = getMap(queryWrapper);
        return Double.parseDouble(String.valueOf(map.get("avg_rating")));
    }

    @Override
    public List<RatingCountDTO> listByNovelId(int i) {
        // 获取相同 novelId 下的评分分布
        // 1: 100; 2: 102: 3: 105; 4: 500; 5: 1000;
        return baseMapper.countRatingsByGroup(i);
    }
}
