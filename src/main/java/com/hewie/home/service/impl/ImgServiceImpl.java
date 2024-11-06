package com.hewie.home.service.impl;

import com.hewie.home.entity.Img;
import com.hewie.home.entity.Photo;
import com.hewie.home.entity.ResponseResult;
import com.hewie.home.mapper.ImgMapper;
import com.hewie.home.service.IImgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hewie.home.utils.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hewie
 * @since 2024-08-25
 */
@Service
public class ImgServiceImpl extends ServiceImpl<ImgMapper, Img> implements IImgService {


    @Autowired
    private IdGenerator idGenerator;

    @Override
    public ResponseResult upload(MultipartFile file) {
        try {
            //文件名
            String fileName = file.getOriginalFilename();
            String id = String.valueOf(idGenerator.nextId());
            String newFileName = id + "." + StringUtils.substringAfterLast(fileName, ".");
            //类型
            String contentType = file.getContentType();

            // 创建图片对象
            byte[] fileBytes = file.getBytes();
            Img img = new Img();
            img.setFilename(newFileName);
            img.setImgBlob(fileBytes);
            img.setId(id);
            save(img);

            Map<String, Object> res = new HashMap<>();
            res.put("id", id);
            res.put("name", newFileName);
            res.put("link", fileBytes);
            return ResponseResult.SUCCESS().setData(res);
        } catch (Exception e) {
            log.error("上传失败", e);
            return ResponseResult.FAILED("上传失败");
        }
    }

    @Override
    public ResponseResult listImgs(int page, int size) {
        int offset = (page - 1) * size;
        return ResponseResult.SUCCESS("获取相册list成功").setData(this.baseMapper.listImg(offset, size));
    }

    @Override
    public ResponseResult listImgList(int page, int size) {
        int offset = (page - 1) * size;
        List<Img> imgList = this.baseMapper.listImg(offset, size);
        return ResponseResult.SUCCESS("获取相册list成功").setData(imgList.stream().map(Img::getImgBlob).collect(Collectors.toList()));
    }

    @Override
    public ResponseResult deleteImg(String id) {
        removeById(id);
        return ResponseResult.SUCCESS().setData("删除成功");
    }
}
