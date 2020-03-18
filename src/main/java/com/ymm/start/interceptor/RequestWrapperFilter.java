package com.ymm.start.interceptor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by yemingming on 2020-03-18.
 */
public class RequestWrapperFilter implements Filter {
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		ServletRequest requestWrapper=null;
		if(servletRequest instanceof HttpServletRequest){
			requestWrapper=new BodyReaderHttpServletRequestWrapper((HttpServletRequest)servletRequest);
		}

		if(requestWrapper==null) {
			System.out.println(111);
			chain.doFilter(servletRequest, servletResponse);
		}else {
			System.out.println(222);
			chain.doFilter(requestWrapper, servletResponse);
		}
	}
}
