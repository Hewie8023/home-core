package com.hewie.home.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hewie.home.entity.Music;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hewie
 * @since 2024-08-15
 */
public interface MusicMapper extends BaseMapper<Music> {

    List<Music> listMusic(int offset, int size);

    Music getRandomMusic();

    @Select("SELECT * FROM t_music")
    IPage<Music> selectPageVo(Page<?> page);
}
