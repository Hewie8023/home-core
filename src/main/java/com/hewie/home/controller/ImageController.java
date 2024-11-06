package com.hewie.home.controller;

import com.hewie.home.entity.ResponseResult;
import com.hewie.home.service.IImgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@Slf4j
public class ImageController {


    @Autowired
    private IImgService imgService;
    /**
     * 文件上传
     *
     * @param file
     */
//    @PostMapping("/img/upload")
    public ResponseResult upload(@RequestParam("file") MultipartFile file) {

        return imgService.upload(file);
    }

    /**
     * 删除c
     *
     * @param fileName
     */
    @DeleteMapping("/")
    public void delete(@RequestParam("fileName") String fileName) {
//        minioUtils.removeFile(minioConfig.getBucketName(), fileName);
    }


//    @GetMapping("/imgs/{page}/{size}")
    public ResponseResult listImgs(@PathVariable("page") int page, @PathVariable("size") int size) {
        return imgService.listImgs(page, size);
    }

//    @GetMapping("/imgList/{page}/{size}")
    public ResponseResult listImgList(@PathVariable("page") int page, @PathVariable("size") int size) {
        return imgService.listImgList(page, size);
    }

//    @PostMapping("/img")
//    public ResponseResult addImg(@RequestBody Photo photo) {
//        return imgService.addImg(photo);
//    }

//    @DeleteMapping("/img/{id}")
    public ResponseResult deleteImg(@PathVariable("id") String id) {
        return imgService.deleteImg(id);
    }

}
