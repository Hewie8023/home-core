package com.hewie.home.service;

import com.hewie.home.entity.Music;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hewie.home.entity.ResponseResult;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.ResponseEntity;

import javax.sound.sampled.AudioInputStream;
import java.io.FileInputStream;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hewie
 * @since 2024-08-15
 */
public interface IMusicService extends IService<Music> {

    ResponseResult addMusic(Music music);

    ResponseResult getMusics(int page, int size, String name);

    void playMusic(String id);

    ResponseResult stopMusic();

    ResponseResult getMusicCover(String id);

    ResponseResult deleteMusic(String id);

    ResponseResult getMusic(String id);

    ResponseResult getRandomMusic();

    ResponseResult modifyMusic(Music music);
}
