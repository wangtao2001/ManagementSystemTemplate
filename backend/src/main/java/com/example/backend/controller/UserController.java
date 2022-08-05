package com.example.backend.controller;

import com.alibaba.fastjson.JSON;
import com.example.backend.common.Result;
import com.example.backend.pojo.User;
import com.example.backend.utils.TokenUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        // 这里先不连数据库
        // 任何登陆都能成功
        String token= TokenUtil.sign(user); // 生成token
        HashMap<String, String> data = new HashMap<>();
        data.put("token", token);
        return Result.success(data);
    }
}
