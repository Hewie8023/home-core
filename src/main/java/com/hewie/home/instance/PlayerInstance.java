package com.hewie.home.instance;

import com.hewie.home.service.Websocket;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.*;
import java.io.FileInputStream;

public class PlayerInstance {

    private static final Logger log = LoggerFactory.getLogger(PlayerInstance.class);

    public static Player player;

    public static Clip clip;

    public static Player getPlayerInstance() {
        return player;
    }

    public static Clip getClipInstance() {
        return clip;
    }

    public static void setClipInstance() {
        try {
            clip = AudioSystem.getClip();
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        Websocket.sendMessage("stop");
                    }
                }
            });
        } catch (LineUnavailableException e) {
            log.error("set clip instance error", e);
        }
    }

    public static void setPlayerInstance(FileInputStream stream) {
        try {
            player = new Player(stream);

        } catch (JavaLayerException e) {
            log.error("set player instance error", e);
        }
    }

}
