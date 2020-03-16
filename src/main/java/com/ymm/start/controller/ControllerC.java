package com.ymm.start.controller;

import com.ymm.start.bean.ABean;
import com.ymm.start.bean.CBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yemingming on 2020/3/15.
 */
@RestController
@RequestMapping("test")
public class ControllerC {

    @PostMapping("c")
    public CBean testA(@RequestBody CBean bean) {
        return bean;
    }
}
