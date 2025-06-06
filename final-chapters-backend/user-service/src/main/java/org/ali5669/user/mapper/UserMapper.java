package org.ali5669.user.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.ali5669.user.domain.po.User;

public interface UserMapper extends BaseMapper<User> {
    default User findByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("username", username);;
        return this.selectOne(queryWrapper); // 返回单个用户

    }

}
