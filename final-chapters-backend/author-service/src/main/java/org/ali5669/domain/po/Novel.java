package org.ali5669.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("novel")
public class Novel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 小说Id
     */
    @TableId(value = "novel_id", type = IdType.AUTO)
    private Integer novelId;

    /**
     * 作者Id
     */
    private Integer authorId;

    /**
     * 标题
     */
    private String title;
    /**
     * 简介
     */
    private String summary;
    /**
     * 类别
     */
    private String category;
    /**
     * 标签
     */
    private String tags;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;
    /**
     * 更新时间
     */
    private LocalDateTime updateAt;
}
