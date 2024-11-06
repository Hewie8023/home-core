package com.hewie.home.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hewie.home.config.MinioConfig;
import com.hewie.home.entity.*;
import com.hewie.home.instance.PlayerInstance;
import com.hewie.home.mapper.MusicMapper;
import com.hewie.home.service.IMusicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hewie.home.service.Websocket;
import com.hewie.home.utils.MinioUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hewie
 * @since 2024-08-15
 */
@Service
@Slf4j
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements IMusicService {

    @Autowired
    public MinioUtils minioUtils;

    @Autowired
    private MinioConfig minioConfig;

    @Value("${web.file-path}")
    private String filePath;

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future<?> future = null;

    private Process process = null;

    @Override
    public ResponseResult addMusic(Music music) {
//        music.setId(music.getPath().split("\\/")[4].split("\\.")[0]);
        save(music);
        return ResponseResult.SUCCESS().setData("添加音乐成功");
    }
    @Override
    public ResponseResult getMusics(int page, int size, String name) {
        int offset = (page - 1) * size;
//        List<Music> musicList = this.baseMapper.listMusic(offset, size);
        Page<Music> musicPage = new Page<>(page, size);
        QueryWrapper<Music> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(name)) {
            queryWrapper.like("music_name", name);
        }
        IPage<Music> musicIPage = this.baseMapper.selectPage(musicPage, queryWrapper);
//        List<MusicVO> musicVOS = new ArrayList<>();
//        for (Music music : musicList) {
//            MusicVO musicVO = new MusicVO();
//            musicVO.setMusic(music);
//            Img img = this.imgMapper.selectById(music.getCover());
//            musicVO.setCoverBlob(img.getImgBlob());
//            musicVOS.add(musicVO);
//        }
        return ResponseResult.SUCCESS("获取音乐list成功").setData(musicIPage);
    }

    public static AudioInputStream loadMusicFromUrl(String urlString) {
        try {
            // 创建一个URL对象
            URL url = new URL(urlString);
            // 使用URL创建一个输入流
            BufferedInputStream bufferedInputStream = new BufferedInputStream(url.openStream());
            // 将输入流转换为AudioInputStream
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
            return audioInputStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResponseResult getMusic(String id) {
        Music music = this.baseMapper.selectById(id);
        MusicEntity entity = new MusicEntity();
        entity.setPic(music.getCover());
        entity.setUrl(music.getPath());
        entity.setAuthor(music.getSinger());
        entity.setTitle(music.getMusicName());
        entity.setId(id);
        return ResponseResult.SUCCESS().setData(entity);
    }

    public ResponseResult getRandomMusic() {
        Music randomMusic = this.baseMapper.getRandomMusic();
        MusicEntity entity = new MusicEntity();
        entity.setPic(randomMusic.getCover());
        entity.setUrl(randomMusic.getPath());
        entity.setAuthor(randomMusic.getSinger());
        entity.setTitle(randomMusic.getMusicName());
        return ResponseResult.SUCCESS().setData(entity);
    }

    @Override
    public ResponseResult modifyMusic(Music music) {
        Music musicDb = this.baseMapper.selectById(music.getId());
        musicDb.setMusicName(music.getMusicName());
        musicDb.setCover(music.getCover());
        musicDb.setCoverName(music.getCoverName());
        musicDb.setSinger(music.getSinger());
        updateById(musicDb);
        return ResponseResult.SUCCESS().setData("修改音乐成功");
    }

    @Override
    public void playMusic(String id) {
//        System.setProperty("javazoom.jl.player.Player", "com.sun.media.sound.DirectAudioDeviceProvider");
        Music music = this.baseMapper.selectById(id);
        try {
            InputStream inputStream = minioUtils.getObject(minioConfig.getBucketName(), music.getId() + ".wav");
            //音乐下载 实际不需要下载，可以在线播放
//            Files.copy(inputStream,Paths.get(filePath + "/source.wav"), StandardCopyOption.REPLACE_EXISTING);
//            File file = new File(filePath + "/source.mp3");
//            FileInputStream fileInputStream = new FileInputStream(file);
//            FileInputStream fileInputStream = StreamUtil.convertToFileInputStream(inputStream);

            play(music.getId());

//            future = executorService.submit(() -> {
//                try {
//                    int i = process.waitFor();
//                    // 播放结束
//                    log.info("aplay 播放结束, code = " + i);
//                    Websocket.sendMessage("stop");
//                } catch (InterruptedException e) {
//                    log.error("aplay执行出错" + e.toString());
//                }
//            });
//            log.info("future=======>" + future);
//            executorService.shutdown();

            // 给前端发送ws消息
//            Websocket.sendMessage(music.getId());

        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    @Async
    public void play(String id) throws Exception {
        stopAplay();
        process = Runtime.getRuntime().exec("aplay " + filePath + "/source.wav");
        // 给前端发送ws消息
        Websocket.sendMessage(id);
        log.info("start play -----> ");
        int i = process.waitFor();
        log.info("aplay 播放结束, code = " + i);
        // 播放结束
//        Thread.sleep(30000);

        Websocket.sendMessage("stop");
        log.info("stop play ---> ");
    }

    private void stopAplay() {
        if (process != null) {
            process.destroy();
            System.out.println("aplay 进程已kill");
            log.info("aplay 进程已kill ====>");
        } else {
            System.out.println("当前没有aplay 进程");
            log.info("当前没有aplay 进程 <====");
        }
//        log.info("---future----:" + future);
//        if (future != null && !future.isDone()) {
//            log.info("stop----future:" + future);
//            future.cancel(true);
//        }
    }

    @Override
    public ResponseResult stopMusic() {
//        this.stop();
        this.stopAplay();
        Websocket.sendMessage("stop");
        return ResponseResult.SUCCESS("停止播放");
    }

    private void stop() {
//        Player player = PlayerInstance.getPlayerInstance();
//        if (player != null) {
//            player.close();
//        }
        Clip clip = PlayerInstance.getClipInstance();
        if (clip != null) clip.stop();
    }

    @Override
    public ResponseResult getMusicCover(String id) {
        Music music = this.baseMapper.selectById(id);
//        Img img = imgMapper.selectById(music.getCover());
        List<Photo> res = new ArrayList<>();
        Photo photo = new Photo();
        photo.setId(music.getId());
        photo.setPhotoName(music.getCoverName());
        photo.setLink(music.getCover());
        photo.setMainColor(music.getMainColor());
        res.add(photo);
        return ResponseResult.SUCCESS().setData(res);
    }

    @Override
    public ResponseResult deleteMusic(String id) {
        Music music = this.baseMapper.selectById(id);
        this.baseMapper.deleteById(id);
        minioUtils.removeFile(minioConfig.getBucketName(), music.getFileName());
        //删除封面
        minioUtils.removeFile(minioConfig.getBucketName(), music.getCoverName());
//        this.imgMapper.deleteById(music.getCover());
        return ResponseResult.SUCCESS().setData("删除成功");
    }

}
