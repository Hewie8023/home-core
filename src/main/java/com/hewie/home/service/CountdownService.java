package com.hewie.home.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class CountdownService {
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> futureTask;

    private final int time = 20;
    private int timeInSeconds = time;

    public void startCountdown() {
        if (futureTask != null && !futureTask.isCancelled()) {
            futureTask.cancel(false);
        }
        futureTask = scheduler.scheduleAtFixedRate(() -> {
            if (timeInSeconds > 0) {
                System.out.println("Remaining time: " + timeInSeconds + " seconds");
                timeInSeconds--;
            } else {
                System.out.println("Countdown finished!");
                futureTask.cancel(false);
                Websocket.sendMessage("off hdmi");
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void resetCountdown() {
        timeInSeconds = time;
        startCountdown();
    }
}
