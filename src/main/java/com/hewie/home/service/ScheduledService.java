package com.hewie.home.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hewie.home.entity.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Slf4j
@Scope("prototype")
public class ScheduledService {
    @Autowired
    ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    ObjectMapper objectMapper;

    Map<String, ScheduledFuture<?>> futureMap;

    @Autowired
    private HomeService homeService;

    private final AtomicBoolean sendPicTaskRunning = new AtomicBoolean(false);
    private final AtomicBoolean hibernateTaskRunning = new AtomicBoolean(false);

    private static final String SEND_PIC_TASK_KEY = "sendPic";
    private static final String HIBERNATE_TASK_KEY = "hibernate";

    public void start(String key) {
        if (StringUtils.isNotBlank(key)) {
            if (futureMap == null) {
                futureMap = new HashMap<>();
            }
            if (SEND_PIC_TASK_KEY.equals(key)) {
                sendPicTaskRunning.set(true);
                ScheduledFuture<?> future1 = threadPoolTaskScheduler.schedule(new SendPicTask(), new CronTrigger("5/5 * * * * ?"));
                futureMap.put(SEND_PIC_TASK_KEY, future1);
                log.info(futureMap.keySet().toString());
            } else if (HIBERNATE_TASK_KEY.equals(key)) {
                hibernateTaskRunning.set(true);
                ScheduledFuture<?> future2 = threadPoolTaskScheduler.schedule(new HibernateTask(), new CronTrigger("0 0/30 * * * ?"));
                futureMap.put(HIBERNATE_TASK_KEY, future2);
                log.info(futureMap.keySet().toString());
            }
        }
    }

    private class SendPicTask implements Runnable {

        @Override
        public void run() {
            if (sendPicTaskRunning.get()) {
                log.info("send pic ... ");
                Map<String, Object> res = new HashMap<>();
                ResponseResult responseResult = homeService.getPhoto();
                res.put("pic", responseResult.getData());
                try {
                    Websocket.sendMessageToOne(objectMapper.writeValueAsString(res), "pics");
                } catch (JsonProcessingException e) {
                    log.error(e.toString());
                    stop(SEND_PIC_TASK_KEY);
                    stop(HIBERNATE_TASK_KEY);
                }
            }
        }
    }

    private class HibernateTask implements Runnable {

        @Override
        public void run() {
            if (hibernateTaskRunning.get()) {
                log.info("hibernate ... ");
                Map<String, Object> res = new HashMap<>();
                res.put("ws", "/clock");
                try {
                    Websocket.sendMessageToOne(objectMapper.writeValueAsString(res), "pics");
                } catch (JsonProcessingException e) {
                    log.info(e.toString());
                }
                stop(SEND_PIC_TASK_KEY);
                stop(HIBERNATE_TASK_KEY);
            }
        }
    }

    public String stop(String key) {
        if (futureMap != null && StringUtils.isNotBlank(key) && futureMap.get(key) != null) {
            futureMap.get(key).cancel(true);
            log.info("stop---->" + key);
            if (SEND_PIC_TASK_KEY.equals(key)) {
                sendPicTaskRunning.set(false);
                log.info(key + "定时任务已结束");
                futureMap.remove(key);
                log.info(futureMap.keySet().toString());
            } else if (HIBERNATE_TASK_KEY.equals(key)) {
                hibernateTaskRunning.set(false);
                log.info(key + "定时任务已结束");
                futureMap.remove(key);
                log.info(futureMap.keySet().toString());
            }
            return key + "定时任务已结束";
        }
        return null;
    }
}
