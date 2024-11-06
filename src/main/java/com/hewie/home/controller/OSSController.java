package com.hewie.home.controller;

import com.hewie.home.config.MinioConfig;
import com.hewie.home.entity.ResponseResult;
import com.hewie.home.utils.ColorUtil;
import com.hewie.home.utils.ColorUtil2;
import com.hewie.home.utils.IdGenerator;
import com.hewie.home.utils.MinioUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/oss")
public class OSSController {

    @Autowired
    private MinioUtils minioUtils;

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private IdGenerator idGenerator;

    /**
     * 文件上传
     *
     * @param file
     */
    @PostMapping("/upload")
    public ResponseResult upload(@RequestParam("file") MultipartFile file) {
        try {
            //文件名
            String fileName = file.getOriginalFilename();
            String id = String.valueOf(idGenerator.nextId());
            String newFileName = id + "." + StringUtils.substringAfterLast(fileName, ".");
            //类型
            String contentType = file.getContentType();
            minioUtils.uploadFile(minioConfig.getBucketName(), file, newFileName, contentType);
            Map<String, String> res = new HashMap<>();
            res.put("id", id);
            res.put("name", newFileName);
            String link = minioUtils.getPresignedObjectUrl(minioConfig.getBucketName(), newFileName);
            if (contentType.contains("image")) {
                res.put("color", ColorUtil2.getColor(file));
                String[] links = link.split("\\?");
                res.put("link", links[0]);
            } else {
                res.put("link", link);
            }
            return ResponseResult.SUCCESS().setData(res);
        } catch (Exception e) {
            log.error("上传失败");
            return ResponseResult.FAILED("上传失败");
        }
    }

    /**
     * 删除c
     *
     * @param fileName
     */
    @DeleteMapping("/")
    public void delete(@RequestParam("fileName") String fileName) {
        minioUtils.removeFile(minioConfig.getBucketName(), fileName);
    }

    /**
     * 获取文件信息
     *
     * @param fileName
     * @return
     */
    @GetMapping("/info")
    public String getFileStatusInfo(@RequestParam("fileName") String fileName) {
        return minioUtils.getFileStatusInfo(minioConfig.getBucketName(), fileName);
    }

    /**
     * 获取文件外链
     *
     * @param fileName
     * @return
     */
    @GetMapping("/url")
    public String getPresignedObjectUrl(@RequestParam("fileName") String fileName) {
        return minioUtils.getPresignedObjectUrl(minioConfig.getBucketName(), fileName);
    }

    /**
     * 文件下载
     *
     * @param fileName
     * @param response
     */
    @GetMapping("/download")
    public void download(@RequestParam("fileName") String fileName, HttpServletResponse response) {
        try {
            InputStream fileInputStream = minioUtils.getObject(minioConfig.getBucketName(), fileName);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载失败");
        }
    }

}
