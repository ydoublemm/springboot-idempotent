package com.ymm.start;

import com.ymm.start.interceptor.ApiIdempotentInterceptor;
import com.ymm.start.interceptor.RequestWrapperFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}



	@Autowired
	private ApiIdempotentInterceptor apiIdempotentInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 接口幂等性拦截器
		registry.addInterceptor(apiIdempotentInterceptor);
		super.addInterceptors(registry);
	}


	@Bean
	public FilterRegistrationBean<RequestWrapperFilter> Filters() {
		FilterRegistrationBean<RequestWrapperFilter> registrationBean = new FilterRegistrationBean<RequestWrapperFilter>();
		registrationBean.setFilter(new RequestWrapperFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setName("RequestWrapperFilter");
		return registrationBean;
	}
}
