package org.ali5669.userservice.domain.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    String username;
    String password;
    String profilePicture;
}
