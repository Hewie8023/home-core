package com.hewie.home.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hewie.home.config.MinioConfig;
import com.hewie.home.entity.Music;
import com.hewie.home.entity.Photo;
import com.hewie.home.entity.ResponseResult;
import com.hewie.home.mapper.PhotoMapper;
import com.hewie.home.service.IPhotoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hewie.home.utils.MinioUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hewie
 * @since 2024-08-15
 */
@Service
@Slf4j
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements IPhotoService {

    @Autowired
    private MinioUtils minioUtils;

    @Autowired
    private MinioConfig minioConfig;
    @Override
    public ResponseResult listPhotos(int page, int size) {
        int offset = (page - 1) * size;
        Page<Photo> photoPage = new Page<>(page, size);
        IPage<Photo> photoIPage = this.baseMapper.selectPage(photoPage, null);
        return ResponseResult.SUCCESS("获取相册list成功").setData(photoIPage);
    }

    @Override
    public ResponseResult addPhoto(Photo photo) {
//        photo.setId(photo.getLink().split("\\/")[4].split("\\.")[0]);
        save(photo);
        return ResponseResult.SUCCESS().setData("添加照片成功");
    }

    @Override
    public ResponseResult deletePhoto(String id) {
        Photo photo = this.baseMapper.selectById(id);
        removeById(id);
        minioUtils.removeFile(minioConfig.getBucketName(), photo.getPhotoName());
        return ResponseResult.SUCCESS().setData("删除成功");
    }

    @Override
    public ResponseResult listPhotoList(int page, int size) {
        int offset = (page - 1) * size;
//        List<Photo> photos = this.baseMapper.listPhoto(offset, size);
//        List<String> res = new ArrayList<>();
//        try {
//            for (Photo photo : photos) {
//                String s = Base64.getEncoder().encodeToString(IOUtils.toByteArray(new URL(photo.getLink())));
//                String img = "data:image/jpeg;base64," + s;
//                res.add(img);
//            }
//        } catch (Exception e) {
//            log.error(String.valueOf(e));
//            return null;
//        }

        return ResponseResult.SUCCESS("获取相册list成功").setData(this.baseMapper.listPhoto(offset, size));
    }

    public List<Photo> getPhotos() {
        return this.baseMapper.selectList(Wrappers.emptyWrapper());
    }
}
