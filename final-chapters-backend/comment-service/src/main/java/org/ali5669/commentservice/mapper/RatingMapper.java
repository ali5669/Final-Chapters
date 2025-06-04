package org.ali5669.commentservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.ali5669.commentservice.domain.dto.RatingCountDTO;
import org.ali5669.commentservice.domain.po.Rating;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RatingMapper extends BaseMapper<Rating> {
    List<RatingCountDTO> countRatingsByGroup(@Param("novelId") Integer novelId);
}
