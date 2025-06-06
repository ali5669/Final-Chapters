package org.ali5669.user.domain.dto;

import lombok.Data;

@Data
public class EditPasswordDTO {
    private String username;
    private String oldPassword;
    private String newPassword;
}