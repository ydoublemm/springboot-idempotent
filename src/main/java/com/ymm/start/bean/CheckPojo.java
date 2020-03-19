package com.ymm.start.bean;

import lombok.Data;

/**
 * Created by yemingming on 2020-03-18.
 */
@Data
public class CheckPojo {
	private String filedValue;

	// 过期时间 毫秒 默认10s
	private int expired;

}
