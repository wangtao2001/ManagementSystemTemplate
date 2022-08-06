package com.example.backend.service;

import com.example.backend.common.Result;
import com.example.backend.mapper.UserMapper;
import com.example.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.regex.Pattern;

@Service
public class UserService {
    private static final String MD5_SECRET = "U76T"; // md5盐

    @Autowired
    private UserMapper userMapper;

    public Result<?> login(String username, String password) {
        User user = userMapper.getUserByUsername(username);
        // 对各种情况做判断
        if(user == null) { // 没有用户
            return Result.error(Result.USERNAME_NOTFOUND_ERROR, Result.USERNAME_NOTFOUND_ERROR_MSG);
        }
        if (user.getAdmin() != 0) { // 没有admin权限
            return Result.error(Result.USER_ADMIN_ERROR, Result.USER_ADMIN_ERROR_MSG);
        }
        if (user.getStatus() != 0) { // 用户已经注销
            return Result.error(Result.USER_CANCEL_ERROR, Result.USER_CANCEL_ERROR_MSG);
        }
        // 加密再比对
        String pass = DigestUtils.md5DigestAsHex((password+MD5_SECRET).getBytes());
        if (!pass.equals(user.getPassword())) { // 密码不匹配
            return Result.error(Result.USERNAME_PWD_ERROR, Result.USERNAME_PWD_ERROR_MSG);
        }
        return Result.success();
    }

    public Result<?> register(String username, String password) {
        User user = userMapper.getUserByUsername(username);
        if(user != null) { // 用户已经存在
            return Result.error(Result.USERNAME_REPEAT_ERROR, Result.USERNAME_REPEAT_ERROR_MSG);
        }
        // 后端验证 账号长度与密码规则
        if ((username.length() ==0 || username.length() >10) && !Pattern.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,10}$", password) ) {
            return Result.error(Result.USERNAME_FORMAT_ERROR, Result.USERNAME_FORMAT_ERROR_MSG); // 绕过前端注册时的格式检查
        }
        // 关于密码选择做后端加密
        // 加密后插入
        userMapper.insert(username, DigestUtils.md5DigestAsHex((password+MD5_SECRET).getBytes()));
        return Result.success();
    }

    public Result<?> getUserByUsername(String username) {
        User user = userMapper.getUserByUsername(username);
        // 这里将user直接返回给前端也没关系，因为使用了jackson排除了密码字段
        return Result.success(user);
    }
}
