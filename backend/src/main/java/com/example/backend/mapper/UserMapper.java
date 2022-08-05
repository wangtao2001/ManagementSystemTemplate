package com.example.backend.mapper;

import com.example.backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    User getUserByUsername(String username);
}
