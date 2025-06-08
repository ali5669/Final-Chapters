package org.ali5669.userservice.service;

import org.ali5669.userservice.domain.dto.*;
import org.ali5669.userservice.domain.po.User;

public interface IUserService {
    void register(RegisterDTO registerDTO);
    User login(LoginFormDTO loginFormDTO);
    Result editProfile(EditProfileDTO editProfileDTO);
    Result editPassword(EditPasswordDTO editPasswordDTO);
    Result becomeVIP(BecomeVipDTO becomeVipDTO);
    Result getUserById(Long userId);
    Result getUser(Long userId);
}
