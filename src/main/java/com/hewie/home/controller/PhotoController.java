package com.hewie.home.controller;

import com.hewie.home.entity.Photo;
import com.hewie.home.entity.ResponseResult;
import com.hewie.home.service.IPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hewie
 * @since 2024-08-15
 */
@RestController
public class PhotoController {
    @Autowired
    private IPhotoService photoService;

    @GetMapping("/photos/{page}/{size}")
    public ResponseResult listPhotos(@PathVariable("page") int page, @PathVariable("size") int size) {
        return photoService.listPhotos(page, size);
    }

    @GetMapping("/photolist/{page}/{size}")
    public ResponseResult listPhotoList(@PathVariable("page") int page, @PathVariable("size") int size) {
        return photoService.listPhotoList(page, size);
    }

    @PostMapping("/photo")
    public ResponseResult addPhoto(@RequestBody Photo photo) {
        return photoService.addPhoto(photo);
    }

    @DeleteMapping("/photo/{id}")
    public ResponseResult deletePhoto(@PathVariable("id") String id) {
        return photoService.deletePhoto(id);
    }
}
