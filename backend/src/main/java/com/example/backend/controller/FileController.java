package com.example.backend.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.example.backend.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 文件相关功能
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${server.port}")
    private String port;

    private static final String URL = "http://localhost";
    // 存储文件的根路径
    private static final String BASE_PATH = System.getProperty("user.dir")+"/backend/src/main/resources/files/";

    /**
     * 文件上传存储
     */
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename(); // 文件名，可能会有重复的问题
        // 加前缀定义文件标识 uuid
        String uuid = IdUtil.fastSimpleUUID();
        String filePath = BASE_PATH
                + uuid
                + "_"
                + fileName; // 保存路径
        FileUtil.writeBytes(file.getBytes(), filePath); // 保存文件
        return Result.success(URL + ":" + port + "/file/download?file=" + uuid); // 返回能够请求该文件的地址
    }

    /**
     * 文件下载，只要传uuid就可以了
     */
    @GetMapping("/download")
    public void download(@RequestParam String file, HttpServletResponse response) throws IOException {
        List<String> fileNames = FileUtil.listFileNames(BASE_PATH); // 路径中所有文件名
        String fileName = fileNames.stream().filter(name -> name.contains(file)).findAny().orElse(""); // 找到与所需fileName一致的文件
        if (StrUtil.isNotEmpty(fileName)) {
            response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, StandardCharsets.UTF_8));
            response.setContentType("application/octet-stream");
            byte[] bytes = FileUtil.readBytes(BASE_PATH + fileName);
            OutputStream out = response.getOutputStream();
            out.write(bytes);
            out.flush();
            out.close();
        }
    }
}
