package org.ali5669.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("chapter")
public class Chapter implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 章节Id
     */
    @TableId(value = "chapter_id", type = IdType.AUTO)
    private Integer chapterId;

    /**
     * 小说Id
     */
    private Integer novelId;
    /**
     * 章节标题
     */
    private String title;
    /**
     * 章节内容
     */
    private String content;
    /**
     * 章节顺序
     */
    @TableField("`order`")
    private Integer order;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
