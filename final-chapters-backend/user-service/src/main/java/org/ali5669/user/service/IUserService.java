package org.ali5669.user.service;

import org.ali5669.user.domain.dto.*;
import org.ali5669.user.domain.po.User;
import org.ali5669.user.domain.vo.UserLoginVO;

public interface IUserService {
    void register(RegisterDTO registerDTO);
    User login(LoginFormDTO loginFormDTO);
    Result editProfile(EditProfileDTO editProfileDTO);
    Result editPassword(EditPasswordDTO editPasswordDTO);
    Result becomeVIP(BecomeVipDTO becomeVipDTO);
}
