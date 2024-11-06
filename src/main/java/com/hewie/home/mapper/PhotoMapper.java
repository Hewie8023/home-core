package com.hewie.home.mapper;

import com.hewie.home.entity.Photo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hewie
 * @since 2024-08-15
 */
public interface PhotoMapper extends BaseMapper<Photo> {

    List<Photo> listPhoto(int offset, int size);
}
