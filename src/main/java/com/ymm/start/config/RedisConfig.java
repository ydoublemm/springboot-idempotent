package com.ymm.start.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yemingming on 2020/3/15.
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Bean
    public RedissonClient getRedissonClient(){
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://"+redisHost+":"+redisPort)
                .setPassword(redisPassword);

        return Redisson.create(config);


    }

}
