package com.hewie.home.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hewie.home.entity.MusicEntity;
import com.hewie.home.entity.Photo;
import com.hewie.home.entity.ResponseResult;
import com.hewie.home.mapper.PhotoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
@Slf4j
public class HomeService {

    private List<MusicEntity> musicEntities;

    private List<Photo> photos = new ArrayList<>();

    private Set<Integer> musicSet = new HashSet<>();

    private Set<Integer> photoSet = new HashSet<>();

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult getMusicList(List<String> ids) {
        musicEntities = new ArrayList<>();
        for (String id : ids) {
            String url = "https://api.roozen.top/?type=playlist&id=" + id;
            ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

            System.out.println(res.getBody());
            List<MusicEntity> musicEntityList = JSON.parseArray(res.getBody(), MusicEntity.class);

            // 处理音乐照片
            if (musicEntityList != null) {
                delPics(musicEntityList);
                musicEntities.addAll(musicEntityList);
            }
            log.info("fetch musics ... " + id);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                log.error("getMusicList Sleep Error ..." + e.toString());
            }
        }

        return ResponseResult.SUCCESS();
    }

    private void delPics(List<MusicEntity> musicEntityList) {
        //https://api.roozen.top?server=netease&type=url&id=108103&auth=a7c460aeaa19802d9b887ded84370dab23ae1ec3
        String urlTemp = "https://music.163.com/api/song/detail/?id=&ids=[";
        for (MusicEntity musicEntity : musicEntityList) {
            String id = musicEntity.getUrl().split("&id=")[1].split("&")[0];
            String url = urlTemp + id + "]";
            ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            Object songs = JSON.parseObject(res.getBody()).get("songs");
            JSONObject album = (JSONObject) ((JSONObject) ((JSONArray) songs).get(0)).get("album");
            String picUrl = (String) album.get("picUrl");
            musicEntity.setPic(picUrl);
        }
    }

    public ResponseResult getRandomMusic() {
        int len = 0;
        if (musicEntities == null || musicEntities.size() == 0) {
            return ResponseResult.FAILED("没有音乐");
        }
        len = musicEntities.size();
        log.info("当前音乐数量=====>" + len);
        if (!musicSet.isEmpty() && musicSet.size() == len) {
            musicSet.clear();
        }
        int n = getRandom(len);
        while (musicSet.contains(n)) {
            n = getRandom(len);
        }
        musicSet.add(n);
        MusicEntity musicEntity = musicEntities.get(n);
        log.info("当前播放第" + n + "首音乐，歌名是===>" + musicEntity.getTitle());

        return ResponseResult.SUCCESS().setData(musicEntity);
    }

    private int getRandom(int len) {
        Random random = new Random();
        return random.nextInt(len);
    }

    @Autowired
    private IPhotoService photoService;

    public void getPhotos() {
        photos = photoService.getPhotos();
    }

    public ResponseResult getPhoto() {
        int len;
        if (photos == null || photos.size() == 0) {
            return ResponseResult.FAILED("没有照片");
        }
        len = photos.size();
        log.info("当前照片数量=====>" + len);
        if (!photoSet.isEmpty() && photoSet.size() == len) {
            photoSet.clear();
        }
        log.info("photo set ===>" + photoSet.size());
        int n = getRandom(len);
        while (photoSet.contains(n)) {
            n = getRandom(len);
        }
        photoSet.add(n);
        Photo photo = photos.get(n);
        log.info("当前展示第" + n + "张图片。。。。。。。");

        return ResponseResult.SUCCESS().setData(photo);
    }

}
