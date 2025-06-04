package org.ali5669.commentservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.ali5669.commentservice.domain.dto.Result;
import org.ali5669.commentservice.domain.po.Comment;

import java.util.List;

public interface ICommentService extends IService<Comment> {
    Result createComment(Comment comment);

    Comment getOne(int id);

    List<Comment> listByNovelId(int novelId);

    List<Comment> listByChapterId(int novelId, int chapterId);
}
