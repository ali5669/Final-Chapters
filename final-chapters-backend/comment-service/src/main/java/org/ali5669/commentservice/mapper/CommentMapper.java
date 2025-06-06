package org.ali5669.commentservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.ali5669.commentservice.domain.po.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
