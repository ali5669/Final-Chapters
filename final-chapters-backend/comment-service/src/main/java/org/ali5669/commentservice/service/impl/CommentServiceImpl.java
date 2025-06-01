package org.ali5669.commentservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.ali5669.commentservice.domain.dto.Result;
import org.ali5669.commentservice.domain.po.Comment;
import org.ali5669.commentservice.mapper.CommentMapper;
import org.ali5669.commentservice.service.ICommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Override
    public Result createComment(Comment comment) {
        if(save(comment)){
            return Result.ok();
        }
        return Result.fail("create comment failed");
    }
}
