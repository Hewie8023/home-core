package com.hewie.home.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hewie
 * @since 2024-11-24
 */
@TableName("t_photo_type")
public class PhotoType implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "PhotoType{" +
            "id = " + id +
            ", name = " + name +
            ", desc = " + desc +
        "}";
    }
}
