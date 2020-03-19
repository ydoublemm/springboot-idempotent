package com.ymm.start.controller;

import com.ymm.start.annotation.ApiIdempotent;
import com.ymm.start.bean.ABean;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yemingming on 2020/3/15.
 */
@RestController
@RequestMapping("test")
public class ControllerA {

    @Autowired
    private RedissonClient redisClient;

    @PostMapping("a")
    public ABean testA(@ApiIdempotent (filed = "nameA") @RequestBody ABean bean) throws InterruptedException {
		Thread.sleep(5000);
        return bean;
    }
}
