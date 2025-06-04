package org.ali5669.commentservice.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("Rating")
public class Rating implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 评分id
     */
    @TableId(value = "rating_id", type = IdType.AUTO)
    private Integer ratingId;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 小说Id
     */
    private Integer novelId;

    /**
     * 评分（1-5）
     */
    private Integer rating;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;
}
