package org.ali5669.userservice.domain.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserVO{
    private Long userId;
    private String username;
    private Boolean isVIP;
    private String profilePicture;
}
