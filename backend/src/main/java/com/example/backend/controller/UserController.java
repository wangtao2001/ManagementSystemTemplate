package com.example.backend.controller;

import com.example.backend.common.Result;
import com.example.backend.pojo.User;
import com.example.backend.service.UserService;
import com.example.backend.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        Result<?> result = userService.login(user.getUsername(), user.getPassword());
        if (result.getCode().equals(Result.SUCCESS)) { // 登陆验证成功
            String token= TokenUtil.sign(user); // 生成token
            HashMap<String, String> data = new HashMap<>();
            data.put("token", token);
            return Result.success(data);
        }
        return result;
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        return userService.register(user.getUsername(), user.getPassword());
    }

    @GetMapping("/current") // 获取当前登陆用户
    public Result<?> current(@RequestHeader("Authorization") String token) {
        String username = TokenUtil.getUserName(token);
        return userService.getUserByUsername(username);
    }
}
