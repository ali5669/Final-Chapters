package org.ali5669.user.domain.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserVO{
    private String username;
    private Boolean isVIP;
    private String profilePicture;
}
