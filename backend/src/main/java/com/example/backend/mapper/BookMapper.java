package com.example.backend.mapper;

import com.example.backend.pojo.Book;
import com.example.backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BookMapper {
    List<Book> get(String keyword); // 模糊查询
    void delete(Integer id);
    void insert(Book book);
    void update(Book book);
}
