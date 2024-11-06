package com.hewie.home.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class StartRunner implements ApplicationRunner {

    @Autowired
    private HomeService homeService;

    @Value("${playlist.id}")
    private List<String> playListIds;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        log.info("Start to fetch music ...");
//        homeService.getMusicList(playListIds);
//        log.info("Fetch music end ...");

        log.info("Start fetch photos ...");
        homeService.getPhotos();
        log.info("Fetch photos end ...");
    }
}
