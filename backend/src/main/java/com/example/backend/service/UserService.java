package com.example.backend.service;

import com.example.backend.common.Result;
import com.example.backend.mapper.UserMapper;
import com.example.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
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
        userMapper.insertByUsername(username, DigestUtils.md5DigestAsHex((password+MD5_SECRET).getBytes()));
        return Result.success();
    }

    // 通过用户名获取user对象
    public Result<?> getUserByUsername(String username) {
        User user = userMapper.getUserByUsername(username);
        // 这里将user直接返回给前端也没关系，因为使用了jackson排除了密码字段
        return Result.success(user);
    }

    // 模糊查询
    public Result<?> get(String keyword) {
        // 还是前端分页，因为数据量还很小
        return Result.success(userMapper.get(keyword));
    }

    // 后台新增用户
    public Result<?> insert(User user) {
        // 判断用户名格式
        // 判断用户名是否存在
        User aUser = userMapper.getUserByUsername(user.getUsername());
        if(aUser != null) { // 用户已经存在
            return Result.error(Result.USERNAME_REPEAT_ERROR, Result.USERNAME_REPEAT_ERROR_MSG);
        }
        if ((user.getUsername().length() ==0 || user.getUsername().length() >10)) {
            return Result.error(Result.USERNAME_FORMAT_ERROR, Result.USERNAME_FORMAT_ERROR_MSG); // 这里前端没有做检查
        }
        // 设置一个默认密码
        user.setPassword(DigestUtils.md5DigestAsHex(("123456"+MD5_SECRET).getBytes())); // 因为密码管理员也不能知道，所以也就不显示指定了
        userMapper.insertByUser(user);
        return Result.success();
    }

    // 更新用户
    public Result<?> update(User user) {
        userMapper.update(user);
        return Result.success();
    }

    // 删除用户
    public Result<?> delete(Integer id) {
        userMapper.delete(id);
        return Result.success();
    }
}
