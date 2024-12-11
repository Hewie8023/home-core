package com.hewie.home.service;

import com.hewie.home.entity.PhotoType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hewie.home.entity.ResponseResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hewie
 * @since 2024-11-24
 */
public interface IPhotoTypeService extends IService<PhotoType> {

    ResponseResult createType(PhotoType photoType);

    ResponseResult modifyType(PhotoType photoType);

    ResponseResult listType(int page, int size);

    ResponseResult deleteType(String id);

}
