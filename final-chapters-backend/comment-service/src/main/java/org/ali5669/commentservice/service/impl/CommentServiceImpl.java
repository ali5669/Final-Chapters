package org.ali5669.commentservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.ali5669.commentservice.domain.dto.Result;
import org.ali5669.commentservice.domain.po.Comment;
import org.ali5669.commentservice.mapper.CommentMapper;
import org.ali5669.commentservice.service.ICommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Override
    public Result createComment(Comment comment) {
        if(save(comment)){
            return Result.ok();
        }
        return Result.fail("create comment failed");
    }

    @Override
    public Comment getOne(int id) {
        return getById(id);
    }

    @Override
    public List<Comment> listByNovelId(int novelId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getNovelId, novelId);
        return list(queryWrapper);
    }

    @Override
    public List<Comment> listByChapterId(int novelId, int chapterId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getNovelId, novelId);
        queryWrapper.eq(Comment::getChapterId, chapterId);
        return list(queryWrapper);
    }
}
