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
@TableName("t_photo")
@Data
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String photoName;

    private String link;

    private String mainColor;

    private String typeId;


    @Override
    public String toString() {
        return "Photo{" +
            "id = " + id +
            ", photoName = " + photoName +
            ", link = " + link +
        "}";
    }
}
