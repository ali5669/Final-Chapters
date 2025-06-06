package org.ali5669.novelservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.ali5669.novelservice.domain.po.Chapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ChapterMapper extends BaseMapper<Chapter> {
    @Select("SELECT MAX(`order`) FROM chapter WHERE novel_id = #{novelId}")
    Integer selectMaxOrder(@Param("novelId") Integer novelId);



}
