package com.hewie.home.mapper;

import com.hewie.home.entity.Img;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hewie
 * @since 2024-08-25
 */
public interface ImgMapper extends BaseMapper<Img> {

    List<Img> listImg(int offset, int size);
}
