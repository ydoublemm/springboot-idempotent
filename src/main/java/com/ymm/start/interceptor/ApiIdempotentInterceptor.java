package com.ymm.start.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.google.common.io.ByteStreams;
import com.ymm.start.annotation.ApiIdempotent;
import com.ymm.start.bean.CheckPojo;
import org.redisson.api.MapOptions;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created by yemingming on 2020-03-17.
 */
@Component
public class ApiIdempotentInterceptor implements HandlerInterceptor {


	@Autowired
	 private RedissonClient redisClient;

	public static String LOCK_PREFIX = "LOCK:";


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		CheckPojo checkPojo = getFiledAndExpire(request, response, handler);

		if(null == checkPojo){
			throw new Exception("请求错误");
		}

		String lockId = LOCK_PREFIX + checkPojo.getFiledValue() ;
		RLock rLock = redisClient.getFairLock(lockId);
		if(!rLock.tryLock(10, checkPojo.getExpired(), TimeUnit.MILLISECONDS)){
			throw new Exception("重复请求");
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		CheckPojo checkPojo = getFiledAndExpire(request, response, handler);

		if(null == checkPojo){
			throw new Exception("111");
		}

		String lockId = LOCK_PREFIX + checkPojo.getFiledValue() ;
		RLock rLock = redisClient.getFairLock(lockId);
		rLock.unlock();
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	public CheckPojo getFiledAndExpire(final HttpServletRequest request, final HttpServletResponse response, final Object handler)throws Exception{
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		Parameter[] parameters = method.getParameters();
		for(int i=0;i<parameters.length;i++){
			ApiIdempotent annotation = parameters[i].getAnnotation(ApiIdempotent.class);
			if(null != annotation){
				String filedName = annotation.filed();
				int expired = annotation.expired();
				String str = new String(ByteStreams.toByteArray(request.getInputStream()));

				Map reqMap = JSONObject.parseObject(str, Map.class);
				String filedVal = (String) reqMap.get(filedName);

				if(StringUtils.isEmpty(filedVal)){
					throw new RuntimeException();
				}
				CheckPojo pojo = new CheckPojo();
				pojo.setExpired(expired);
				pojo.setFiledValue(filedVal);

				return pojo;

			}
		}

		return null;
	}
}
