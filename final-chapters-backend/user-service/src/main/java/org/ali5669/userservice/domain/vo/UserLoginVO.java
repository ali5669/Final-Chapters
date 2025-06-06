package org.ali5669.userservice.domain.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginVO {
    private UserVO user;
    private String token;
}
