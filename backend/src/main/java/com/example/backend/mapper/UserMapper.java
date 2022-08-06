package com.example.backend.mapper;

import com.example.backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    User getUserByUsername(String username);
    void insertByUsername(String username, String password);
    List<User> get(String keyword); // 模糊查询
    void insertByUser(User user);
    void update(User user);
    void delete(Integer id);
}
