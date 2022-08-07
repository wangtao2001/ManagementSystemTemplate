package com.example.backend.service;

import com.example.backend.common.Result;
import com.example.backend.mapper.BookMapper;
import com.example.backend.pojo.Book;
import com.example.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    BookMapper bookMapper;

    public Result<?> get(String keyword) {
        return Result.success(bookMapper.get(keyword));
    }

    public Result<?> delete(Integer id) {
        bookMapper.delete(id);
        return Result.success();
    }

    public Result<?> insert(Book book) {
        bookMapper.insert(book);
        return Result.success();
    }

    public Result<?> update(Book book) {
        bookMapper.update(book);
        return Result.success();
    }
}
