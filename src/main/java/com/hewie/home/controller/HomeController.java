package com.hewie.home.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hewie.home.entity.ResponseResult;
import com.hewie.home.service.HomeService;
import com.hewie.home.service.IMusicService;
import com.hewie.home.service.ScheduledService;
import com.hewie.home.service.Websocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HomeController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ScheduledService scheduledService;

    @Autowired
    private HomeService homeService;

    @Autowired
    private IMusicService musicService;

    @GetMapping("/to-pics")
    public ResponseResult toPics() throws JsonProcessingException {
        Map<String, Object> res = new HashMap<>();
        res.put("ws", "/pics");
        Websocket.sendMessage(objectMapper.writeValueAsString(res));
        return ResponseResult.SUCCESS().setData(res);
    }

    @GetMapping("/to-clock")
    public ResponseResult toClock() throws JsonProcessingException {
        Map<String, Object> res = new HashMap<>();
        res.put("ws", "/clock");
        Websocket.sendMessage(objectMapper.writeValueAsString(res));
        return ResponseResult.SUCCESS().setData(res);
    }

    @GetMapping("to-music")
    public ResponseResult toMusic(@RequestParam("id") String id) throws JsonProcessingException {
        Map<String, Object> res = new HashMap<>();
        res.put("ws", "/playMusic/" + id);
        Websocket.sendMessage(objectMapper.writeValueAsString(res));
        return ResponseResult.SUCCESS().setData(res);
    }

    @GetMapping("/start/task")
    public ResponseResult startTask() {
        //  启动定时任务，每30s向前端发送一张图片
        scheduledService.start("sendPic");
        scheduledService.start("hibernate");
        return null;
    }

    @GetMapping("/stop/task")
    public ResponseResult stopTask() {
        // 关闭定时任务，如果多次请求，只处理一次
        scheduledService.stop("sendPic");
        scheduledService.stop("hibernate");
        return null;
    }

    @GetMapping("/music/refresh")
    public ResponseResult refreshMusicList(@RequestParam("ids") List<String> ids) {
        return homeService.getMusicList(ids);
    }

    @GetMapping("/music/random")
    public ResponseResult getRandomMusic() {
        return musicService.getRandomMusic();
    }

    @GetMapping("/music/local")
    public ResponseResult getLocalMusic(@RequestParam("id") String id) {
        return musicService.getMusic(id);
    }

    @GetMapping("/photo/random")
    public ResponseResult getRandomPhoto() {
        return homeService.getPhoto();
    }

    @GetMapping("/photo/refresh")
    public ResponseResult refreshPhotos() {
         homeService.getPhotos();
         return ResponseResult.SUCCESS();
    }


}
