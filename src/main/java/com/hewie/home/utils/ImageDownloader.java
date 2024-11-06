package com.hewie.home.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.sql.Blob;
import java.nio.file.Files;
import javax.sql.rowset.serial.SerialBlob;

@Slf4j
public class ImageDownloader {

    public static Blob downloadImageAsBlob(String imageUrl) {

        try {
            URL url = new URL(imageUrl);
            InputStream inputStream = url.openStream();
            byte[] bytes = IOUtils.toByteArray(inputStream);
            inputStream.close();
            return new SerialBlob(bytes);
        } catch (Exception e) {
            log.error("download image error" + e);
            return null;
        }
    }


}