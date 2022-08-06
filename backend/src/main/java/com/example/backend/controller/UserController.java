package com.example.backend.controller;

import com.example.backend.common.Result;
import com.example.backend.pojo.User;
import com.example.backend.service.UserService;
import com.example.backend.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/current") // 获取当前登录用户
    public Result<?> current(@RequestHeader("Authorization") String token) {
        String username = TokenUtil.getUserName(token);
        return userService.getUserByUsername(username);
    }

    @GetMapping("/get") // 模糊查询
    public Result<?> get(@RequestParam(defaultValue = "") String keyword) {
        return userService.get(keyword);
    }

    @PostMapping("/insert")
    public Result<?> insert(@RequestBody User user) {
        return userService.insert(user);
    }

    @PostMapping("/update")
    public Result<?> update(@RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping("/delete")
    public Result<?> delete(@RequestBody Map<String,Integer> map) {
        Integer id = map.get("id");
        return userService.delete(id);
    }

    // 这里对应多种获取请求参数的方式
}
