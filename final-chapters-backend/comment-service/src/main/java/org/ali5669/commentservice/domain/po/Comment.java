package org.ali5669.commentservice.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("comment")
public class Comment implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 评论Id
     */
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Integer commentId;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 小说Id
     */
    private Integer novelId;

    /**
     * 章节Id
     */
    private Integer chapterId;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;
}
