package com.hewie.home.config;

import com.hewie.home.entity.MusicEntity;
import com.hewie.home.entity.Photo;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Data
public class GlobalStoreConfig {
    private List<MusicEntity> musicEntities = new ArrayList<>();

    private List<Photo> photos = new ArrayList<>();

    private Set<Integer> musicSet = new HashSet<>();

    private Set<Integer> photoSet = new HashSet<>();

}
