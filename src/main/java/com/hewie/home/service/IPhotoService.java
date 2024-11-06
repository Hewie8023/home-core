package com.hewie.home.service;

import com.hewie.home.entity.Photo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hewie.home.entity.ResponseResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hewie
 * @since 2024-08-15
 */
public interface IPhotoService extends IService<Photo> {

    ResponseResult listPhotos(int page, int size);

    ResponseResult addPhoto(Photo photo);

    ResponseResult deletePhoto(String id);

    ResponseResult listPhotoList(int page, int size);

    public List<Photo> getPhotos();
}
