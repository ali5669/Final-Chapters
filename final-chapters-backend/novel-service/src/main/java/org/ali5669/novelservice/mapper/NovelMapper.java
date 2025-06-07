package org.ali5669.novelservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.ali5669.novelservice.domain.po.Novel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NovelMapper extends BaseMapper<Novel> {
}
