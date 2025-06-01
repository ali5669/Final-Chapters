package org.ali5669.commentservice.controller;

import lombok.RequiredArgsConstructor;
import org.ali5669.commentservice.domain.dto.CommentCreateDTO;
import org.ali5669.commentservice.domain.dto.Result;
import org.ali5669.commentservice.domain.po.Comment;
import org.ali5669.commentservice.service.ICommentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final ICommentService commentService;

    @PostMapping
    public Result addComment(@RequestBody CommentCreateDTO createDTO) {
        Integer userIdInt = Integer.parseInt(createDTO.getUserId());
        Integer novelIdInt = Integer.parseInt(createDTO.getNovelId());
        Integer chapterIdInt = Integer.parseInt(createDTO.getChapterId());
        String content = createDTO.getContent();

        Comment comment = new Comment();
        comment.setUserId(userIdInt);
        comment.setNovelId(novelIdInt);
        comment.setChapterId(chapterIdInt);
        comment.setContent(content);

        return commentService.createComment(comment);
    }
}
