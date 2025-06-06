package org.ali5669.user.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class JwtProperties {


    private String userSecretKey;

    @Value("${jwt.user.ttl}")
    private long userTtl;

    @Value("${jwt.user.token-name}")
    private String userTokenName;


}
