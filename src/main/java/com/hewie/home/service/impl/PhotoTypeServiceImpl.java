package com.hewie.home.service.impl;

import com.hewie.home.entity.Photo;
import com.hewie.home.entity.PhotoType;
import com.hewie.home.entity.ResponseResult;
import com.hewie.home.mapper.PhotoMapper;
import com.hewie.home.mapper.PhotoTypeMapper;
import com.hewie.home.service.IPhotoTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hewie
 * @since 2024-11-24
 */
@Service
public class PhotoTypeServiceImpl extends ServiceImpl<PhotoTypeMapper, PhotoType> implements IPhotoTypeService {

    @Override
    public ResponseResult createType(PhotoType photoType) {
        PhotoType selectByName = this.baseMapper.selectByName(photoType.getName());
        if (ObjectUtils.isNotEmpty(selectByName)) {
            return ResponseResult.FAILED("已经添加该类型");
        }
        save(photoType);
        return ResponseResult.SUCCESS("添加成功");
    }

    @Override
    public ResponseResult modifyType(PhotoType photoType) {
        PhotoType selectByName = this.baseMapper.selectByName(photoType.getName());
        if (ObjectUtils.isNotEmpty(selectByName)) {
            return ResponseResult.FAILED("类型名称重复");
        }
        selectByName.setName(photoType.getName());
        selectByName.setDesc(photoType.getDesc());
        updateById(selectByName);
        return ResponseResult.SUCCESS("修改类型成功");
    }

    @Override
    public ResponseResult listType(int page, int size) {
        return null;
    }

    @Override
    public ResponseResult deleteType(String id) {
        //删除之前需要确认有没有照片关联
        List<Photo> photos = this.baseMapper.selectPhotoByTypeId(id);
        if (photos.size() > 0) {
            return ResponseResult.FAILED("删除失败，该类型下有照片存放");
        }
        deleteType(id);
        return ResponseResult.SUCCESS("删除照片类型成功");
    }


}
