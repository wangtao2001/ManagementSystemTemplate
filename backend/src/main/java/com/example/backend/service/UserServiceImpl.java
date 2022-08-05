package com.example.backend.service;

import com.example.backend.common.Result;
import com.example.backend.mapper.UserMapper;
import com.example.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<?> login(String username, String password) {
        User user = userMapper.getUserByUsername(username);
        if(user == null) {
            return Result.error(Result.USERNAME_NOTFOUND_ERROR, Result.USERNAME_NOTFOUND_ERROR_MSG);
        }
        if (password.equals(user.getPassword())) {
            return Result.success();
        }
        return Result.error(Result.USERNAME_PWD_ERROR, Result.USERNAME_PWD_ERROR_MSG);
    }
}
