package com.example.backend.service;

import com.example.backend.common.Result;

public interface UserService {
    Result<?> login(String username, String password);
    Result<?> register(String username, String password);
}
