package com.hewie.home.controller;

import com.hewie.home.entity.PhotoType;
import com.hewie.home.entity.ResponseResult;
import com.hewie.home.service.IPhotoTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hewie
 * @since 2024-11-24
 */
@Controller
@RequestMapping("/home/photoType")
public class PhotoTypeController {

    @Autowired
    private IPhotoTypeService photoTypeService;

    @PostMapping
    public ResponseResult createType(PhotoType photoType) {
        return photoTypeService.createType(photoType);
    }

    @PutMapping
    public ResponseResult modifyType(PhotoType photoType) {
        return photoTypeService.modifyType(photoType);
    }

    @GetMapping("/{page}/{size}")
    public ResponseResult listType(@PathVariable("page") int page,
                                   @PathVariable("size") int size) {
        return photoTypeService.listType(page, size);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteType(@PathVariable("id") String id) {
        return photoTypeService.deleteType(id);
    }

}
