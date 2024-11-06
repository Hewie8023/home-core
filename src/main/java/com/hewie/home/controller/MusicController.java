package com.hewie.home.controller;

import com.hewie.home.entity.Music;
import com.hewie.home.entity.ResponseResult;
import com.hewie.home.service.AsyncService;
import com.hewie.home.service.CountdownService;
import com.hewie.home.service.IMusicService;
import com.hewie.home.service.Websocket;
import javazoom.jl.decoder.JavaLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hewie
 * @since 2024-08-15
 */
@RestController
public class MusicController {

    @Autowired
    public IMusicService musicService;

    @Autowired
    private AsyncService asyncService;


    @PostMapping("/music")
    public ResponseResult addMusic(@RequestBody Music music) {
        return musicService.addMusic(music);
    }

    @PutMapping("/music")
    public ResponseResult modifyMusic(@RequestBody Music music) {
        return musicService.modifyMusic(music);
    }

    @DeleteMapping("/music/{id}")
    public ResponseResult deleteMuSic(@PathVariable("id") String id) {
        return musicService.deleteMusic(id);
    }

//    @GetMapping("/music/{id}/play")
    public ResponseResult playMuSic(@PathVariable("id") String id) {
        try {
            Websocket.sendMessage("on hdmi");
//            Thread.sleep(1000);
            System.out.println("............");
            countdownService.resetCountdown();
            musicService.playMusic(id);
//            asyncService.play(musicService.playMusic(id));
        } catch (Exception e) {
            return ResponseResult.FAILED("播放失败");
        }
        return ResponseResult.SUCCESS("播放成功");
    }

//    @GetMapping("/music/stop")
    public ResponseResult stopMuSic() {
        return musicService.stopMusic();
    }

    @GetMapping("/musics/{page}/{size}")
    public ResponseResult listMusic(@PathVariable("page") int page, @PathVariable("size") int size, @RequestParam("name") String name) {
        return musicService.getMusics(page, size, name);
    }

//    @GetMapping("/music/{id}/cover")
    public ResponseResult getMusicCover(@PathVariable("id") String id) {
        return musicService.getMusicCover(id);
    }

//    @GetMapping("/hdmi/on")
    public ResponseResult onHdmi() {
        System.out.println("on hdmi");
        Websocket.sendMessage("on hdmi");
        countdownService.resetCountdown();
        return ResponseResult.SUCCESS().setData("打开显示器成功");
    }

    @Autowired
    private CountdownService countdownService;

}
