package org.ali5669.commentservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.ali5669.commentservice.domain.dto.Result;
import org.ali5669.commentservice.domain.po.Comment;

public interface ICommentService extends IService<Comment> {
    Result createComment(Comment comment);
}
