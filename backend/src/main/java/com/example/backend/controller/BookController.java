package com.example.backend.controller;

import com.example.backend.common.Result;
import com.example.backend.pojo.Book;
import com.example.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/get")
    public Result<?> get(@RequestParam(defaultValue = "") String keyword) {
        return bookService.get(keyword);
    }

    @PostMapping("/delete")
    public Result<?> delete(@RequestBody Map<String,Integer> map) {
        Integer id = map.get("id");
        return bookService.delete(id);
    }

    @PostMapping("/insert")
    public Result<?> insert(@RequestBody Book book) {
        return bookService.insert(book);
    }
    @PostMapping("/update")
    public Result<?> update(@RequestBody Book book) {
        return bookService.update(book);
    }
}
