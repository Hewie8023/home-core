package com.hewie.home.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hewie
 * @since 2024-08-15
 */
@TableName("t_music")
@Data
public class Music implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String musicName;

    private String path;

    private String singer;

    private String cover;

    private String coverName;

    private String fileName;

    private String mainColor;



    @Override
    public String toString() {
        return "Music{" +
            "id = " + id +
            ", musicName = " + musicName +
            ", path = " + path +
            ", singer = " + singer +
            ", cover = " + cover +
            ", coverName = " + coverName +
            ", fileName = " + fileName +
        "}";
    }
}
