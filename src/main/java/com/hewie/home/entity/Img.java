package com.hewie.home.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hewie
 * @since 2024-08-25
 */
@TableName("t_img")
public class Img implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private byte[] imgBlob;

    private String filename;

    public String getFilename() {return filename;}

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getImgBlob() {
        return imgBlob;
    }

    public void setImgBlob(byte[] imgBlob) {
        this.imgBlob = imgBlob;
    }

    @Override
    public String toString() {
        return "Img{" +
            "id = " + id +
            ", imgBlob = " + imgBlob +
        "}";
    }
}
