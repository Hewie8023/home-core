package com.hewie.home.service;

import com.hewie.home.instance.PlayerInstance;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class AsyncService {
    @Async
    public void play(String path) throws JavaLayerException, InterruptedException, LineUnavailableException, IOException {
//        PlayerInstance.setPlayerInstance(fileInputStream);
//        PlayerInstance.setClipInstance();
//        Player player = PlayerInstance.getPlayerInstance();
//        player.play();
//        Clip clip = PlayerInstance.getClipInstance();
//        clip.open(fileInputStream);
//        clip.start();
//        while (!player.isComplete()) {
//            Thread.sleep(100);
//        }
//        Websocket.sendMessage("stop");


    }

}
