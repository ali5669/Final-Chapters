package org.ali5669.userservice.controller;

import org.ali5669.userservice.domain.dto.BecomeVipDTO;
import org.ali5669.userservice.domain.dto.EditPasswordDTO;
import org.ali5669.userservice.domain.dto.EditProfileDTO;
import org.ali5669.userservice.domain.dto.LoginFormDTO;
import org.ali5669.userservice.domain.dto.RegisterDTO;
import org.ali5669.userservice.domain.dto.Result;
import org.ali5669.userservice.domain.po.User;
import org.ali5669.userservice.domain.vo.UserLoginVO;
import org.ali5669.userservice.domain.vo.UserVO;
import org.ali5669.userservice.service.IUserService;
import org.ali5669.userservice.util.JwtUtil;
import org.ali5669.userservice.config.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    // 用户登录
    @PostMapping("/login")
    public Result login(@RequestBody LoginFormDTO loginFormDTO) {
        User user = userService.login(loginFormDTO);
        Map<String, Object> claims = new HashMap<>();
        claims.put("empId", user.getId());
        String token = JwtUtil.createJWT(
                generateRandomKey(),
                jwtProperties.getUserTtl(),
                claims);
        UserVO userVO = UserVO.builder()
                .username(user.getUsername())
                .isVIP(user.getIsVip())
                .profilePicture(user.getProfilePicture())
                .build();
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .user(userVO)
                .token(token)
                .build();
        return Result.ok(userLoginVO);
    }

    // 用户注册
    @PostMapping("/register")
    public Result register(@RequestBody RegisterDTO registerDTO) {
        userService.register(registerDTO);
        return Result.ok();
    }

    @PostMapping("/editProfile")
    public Result editProfile(@RequestBody EditProfileDTO editProfileDTO) {
        return userService.editProfile(editProfileDTO);
    }

    @PostMapping("/editPassword")
    public Result editPassword(@RequestBody EditPasswordDTO editPasswordDTO) {
        return userService.editPassword(editPasswordDTO);
    }

    @PostMapping("/becomeVIP")
    public Result becomeVIP(@RequestBody BecomeVipDTO becomeVipDTO) {
        return userService.becomeVIP(becomeVipDTO);
    }
    private static String generateRandomKey() {
        byte[] key = new byte[32];
        new SecureRandom().nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
