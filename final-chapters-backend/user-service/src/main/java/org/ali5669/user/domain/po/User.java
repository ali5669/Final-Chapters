package org.ali5669.user.domain.po;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("user")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value="user_id",type=IdType.AUTO)
    private Long id;
    private Boolean isVip;
    private String username;
    private String password;
    private String profilePicture;
}
