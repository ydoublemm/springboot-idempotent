package com.ymm.start.annotation;

import java.lang.annotation.*;

/**
 * Created by yemingming on 2020-03-16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.PARAMETER})
@Inherited
public @interface ApiIdempotent {

	String filed();

	// 过期时间 毫秒 默认10s
	int expired() default 10000;

}
