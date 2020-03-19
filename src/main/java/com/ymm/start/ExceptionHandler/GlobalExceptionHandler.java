package com.ymm.start.ExceptionHandler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value = Exception.class)
	public String exceptionHandler(HttpServletRequest request, Exception e){

		if(e.getMessage().equals("重复请求")){
			return "重复请求";
		}

		return "其他异常";

	}
}

