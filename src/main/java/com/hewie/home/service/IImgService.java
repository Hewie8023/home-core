package com.hewie.home.service;

import com.hewie.home.entity.Img;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hewie.home.entity.Photo;
import com.hewie.home.entity.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hewie
 * @since 2024-08-25
 */
public interface IImgService extends IService<Img> {

    ResponseResult upload(MultipartFile file);

    ResponseResult listImgs(int page, int size);

    ResponseResult listImgList(int page, int size);

    ResponseResult deleteImg(String id);
}
