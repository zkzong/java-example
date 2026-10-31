package org.example.sb3.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 上传文件
 *
 * @Author: zongz
 * @Date: 2024/10/26
 */
@RestController
@RequestMapping("/upload")
@Slf4j
public class UploadController {
    /**
     * 单文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/file")
    public String file(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("文件名称：{}", file.getOriginalFilename());
        log.info("文件大小：{}", file.getSize());
        log.info("文件类型：{}", file.getContentType());
        //log.info("文件内容：{}", file.getBytes());
        log.info("文件内容：{}", file.getInputStream());
        log.info("文件内容：{}", file.getResource());
        return "上传成功";
    }

    /**
     * 多文件上传
     *
     * @param files
     * @return
     */
    @PostMapping("/files")
    public String files(@RequestParam("files") MultipartFile[] files) throws IOException {
        for (MultipartFile file : files) {
            log.info("文件名称：{}", file.getOriginalFilename());
            log.info("文件大小：{}", file.getSize());
            log.info("文件类型：{}", file.getContentType());
            //log.info("文件内容：{}", file.getBytes());
            log.info("文件内容：{}", file.getInputStream());
            log.info("文件内容：{}", file.getResource());
        }
        return "上传成功";
    }
}