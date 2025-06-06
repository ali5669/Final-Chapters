package org.ali5669.userservice.domain.dto;

import lombok.Data;

@Data
public class BecomeVipDTO {
    private String username;
    private Boolean isVIP;

    public Boolean isVIP() {
        return this.isVIP;
    }
}