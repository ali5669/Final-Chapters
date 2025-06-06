package org.ali5669.user.domain.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginVO {
    private UserVO user;
    private String token;
}
