package com.hewie.home.config;

import com.hewie.home.utils.IdGenerator;
import com.hewie.home.utils.SnowflakeIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdGeneratorConfig {

    @Bean
    public IdGenerator snowflakeIdGenerator() {
        // 这里的workerId和datacenterId需要根据实际情况进行配置
        return new SnowflakeIdGenerator(0, 0);
    }
}