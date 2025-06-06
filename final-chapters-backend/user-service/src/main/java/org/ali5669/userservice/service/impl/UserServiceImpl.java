package org.ali5669.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.ali5669.userservice.domain.dto.*;
import org.ali5669.userservice.domain.po.User;
import org.ali5669.userservice.exception.BaseException;
import org.ali5669.userservice.mapper.UserMapper;
import org.ali5669.userservice.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserMapper userMapper;


    @Override
    public void register(RegisterDTO registerDTO) {

        // 检查用户名是否已存在
        User existingUser = userMapper.findByUsername(registerDTO.getUsername());
        if (existingUser != null) {
            throw new BaseException("用户名重复");
        }
        String encodedPassword = DigestUtils.md5DigestAsHex(registerDTO.getPassword().getBytes());
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(encodedPassword);
        user.setProfilePicture(registerDTO.getProfilePicture());
        user.setIsVip(false);
        userMapper.insert(user);
    }

    @Override
    public User login(LoginFormDTO loginFormDTO) {
        String username = loginFormDTO.getUsername();
        String password = loginFormDTO.getPassword();

        User user = userMapper.findByUsername(username);
        if (user == null) {
            //账号不存在
            throw new BaseException("账号不存在");
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new BaseException("用户名或密码错误");
        }
        return user;
    }

    @Override
    public Result editProfile(EditProfileDTO editProfileDTO) {
        User currentUser = userMapper.findByUsername(editProfileDTO.getUsername());

        currentUser.setProfilePicture(editProfileDTO.getProfilePicture());
        userMapper.updateById(currentUser);
        return Result.ok();
    }

    @Override
    public Result editPassword(EditPasswordDTO editPasswordDTO) {
        User currentUser = userMapper.findByUsername(editPasswordDTO.getUsername());
        // 验证旧密码是否正确

        System.out.println(currentUser.getPassword());
        String encodedOldPassword = DigestUtils.md5DigestAsHex(editPasswordDTO.getOldPassword().getBytes());
        System.out.println(encodedOldPassword);
        if (!encodedOldPassword.equals(currentUser.getPassword())) {
            throw new BaseException("旧密码错误");
        }
        // 更新为新密码
        String encodedNewPassword = DigestUtils.md5DigestAsHex(editPasswordDTO.getNewPassword().getBytes());
        currentUser.setPassword(encodedNewPassword);
        userMapper.updateById(currentUser);
        return Result.ok();
    }

    @Override
    public Result becomeVIP(BecomeVipDTO becomeVipDTO) {
        User currentUser = userMapper.findByUsername(becomeVipDTO.getUsername());
        currentUser.setIsVip(!becomeVipDTO.isVIP());
        userMapper.updateById(currentUser);
        return Result.ok(currentUser);
    }

}