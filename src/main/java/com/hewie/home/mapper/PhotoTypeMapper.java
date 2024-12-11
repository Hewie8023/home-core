package com.hewie.home.mapper;

import com.hewie.home.entity.Photo;
import com.hewie.home.entity.PhotoType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hewie
 * @since 2024-11-24
 */
public interface PhotoTypeMapper extends BaseMapper<PhotoType> {

    @Select("SELECT * FROM t_photo_type WHERE name=#{name}")
    PhotoType selectByName(String name);

    List<Photo> selectPhotoByTypeId(String id);
}
