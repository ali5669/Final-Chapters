package org.ali5669.userservice.domain.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EditProfileDTO {
    private String username;
    private MultipartFile profilePicture;
}