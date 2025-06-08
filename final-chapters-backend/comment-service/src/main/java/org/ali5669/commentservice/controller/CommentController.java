package org.ali5669.commentservice.controller;

import lombok.RequiredArgsConstructor;
import org.ali5669.commentservice.domain.dto.CommentCreateDTO;
import org.ali5669.commentservice.domain.dto.Result;
import org.ali5669.commentservice.domain.po.Comment;
import org.ali5669.commentservice.service.ICommentService;
import org.ali5669.commentservice.utils.UserContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final ICommentService commentService;

    @PostMapping
    public Result addComment(@RequestBody CommentCreateDTO createDTO) {
        Integer userIdInt = null;
        Integer novelIdInt = null;
        Integer chapterIdInt = null;
        String content = null;
        try{
            userIdInt = UserContext.getUserId();
            novelIdInt = Integer.parseInt(createDTO.getNovelId());
            // 处理 chapterId 为可空情况
            if (createDTO.getChapterId() != null && !createDTO.getChapterId().isEmpty()) {
                chapterIdInt = Integer.parseInt(createDTO.getChapterId());
            }
            content = createDTO.getContent();
        } catch (Exception e){
            return Result.fail("参数错误: " + e);
        }

        Comment comment = new Comment();
        comment.setUserId(userIdInt);
        comment.setNovelId(novelIdInt);
        comment.setChapterId(chapterIdInt);
        comment.setContent(content);

        return commentService.createComment(comment);
    }

    @GetMapping("{id}")
    public Result getOne(@PathVariable String id){
         return Result.ok(commentService.getOne(Integer.parseInt(id)));
    }

    @GetMapping("/novelList/{novelId}")
    public Result listByNovelId(@PathVariable String novelId){
        return Result.ok(commentService.listByNovelId(Integer.parseInt(novelId)));
    }

    @GetMapping("/chapterList/{novelId}/{chapterId}")
    public Result listByChapterId(@PathVariable String novelId, @PathVariable String chapterId){
        return Result.ok(commentService.listByChapterId(Integer.parseInt(novelId), Integer.parseInt(chapterId)));
    }
}
