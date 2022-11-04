package com.pegasus.test.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.pegasus.test.service.auth.AuthService;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

	private final Logger log = LogManager.getLogger(getClass());

	private static final String AUTH_HEADER_PARAMETER_AUTHERIZATION = "authorization";

	@Value("${app.api.basic.username}")
	private String userName;

	@Value("${app.api.basic.password}")
	private String password;
	

	

	@Autowired
	private AuthService authService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		Boolean isValidBasicAuthRequest = false;
		if(request.getRequestURI().contains("swagger-ui") || request.getRequestURI().contains("api-docs")) {
			return true;
		}

		log.info("[Inside PRE Handle interceptor][" + request + "]" + "[" + request.getMethod() + "]"
				+ request.getRequestURI());

		try {

			// Grab basic header value from request header object.
			String basicAuthHeaderValue = request.getHeader(AUTH_HEADER_PARAMETER_AUTHERIZATION);

			// Process basic authentication
			isValidBasicAuthRequest = authService.validateBasicAuthentication(userName, password, basicAuthHeaderValue);

			// If this is invalid request, then set the status as UNAUTHORIZED.
			if (!isValidBasicAuthRequest) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
			}

		} catch (Exception e) {
			log.error("Error occured while authenticating request : " + e.getMessage());
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}

		return isValidBasicAuthRequest;

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		log.info("[Inside POST Handle Interceptor]" + request.getRequestURI());

	}

}