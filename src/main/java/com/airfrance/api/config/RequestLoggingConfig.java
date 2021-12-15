package com.airfrance.api.config;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Configuration spring boot for request logging
 * @author Vezolles
 */
@Configuration
@Aspect
@Slf4j
public class RequestLoggingConfig {
	
	/**
	 * Request of incoming request
	 */
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * Mapper user to convert Object to String
	 */
	@Autowired
	private ObjectMapper mapper;
	
	/**
	 *  Log GET incoming request
	 * 
	 * @param joinPoint join point used with aspect
	 * @return result of method execution
	 * @throws Throwable if an error occurs
	 */
	@Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public Object logAroundGetMapping(ProceedingJoinPoint joinPoint) throws Throwable {

		Object result = null;
		
		// Log incoming request
		log.info("GET " + request.getRequestURL());
		long startTime = System.currentTimeMillis();
		
		try {
			// Proceed method
			result = joinPoint.proceed();
			return result;
		
		} finally {
	        long endtime = System.currentTimeMillis();
	        
	        // Log end incoming request
	        log.info("GET " + request.getRequestURL() + " in " + (endtime-startTime) + "ms with body : " + mapper.writeValueAsString(result));
		}
    }
	
	/**
	 *  Log POST incoming request
	 * 
	 * @param joinPoint join point used with aspect
	 * @return result of method execution
	 * @throws Throwable if an error occurs
	 */
	@Around("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public Object logAroundPostMapping(ProceedingJoinPoint joinPoint) throws Throwable {
		
		Object result = null;
		String body = request.getReader().lines().collect(Collectors.joining());
		
		// Log incoming request
		log.info("POST " + request.getRequestURL() + " with body : " + body);
		long startTime = System.currentTimeMillis();
		
		try {
			// Proceed method
			result = joinPoint.proceed();
			return result;
		
		} finally {
	        long endtime = System.currentTimeMillis();
	        
	        // Log end incoming request
	        log.info("POST " + request.getRequestURL() + " in " + (endtime-startTime) + "ms with body : " + mapper.writeValueAsString(result));
		}
    }
	
	/**
	 *  Log DELETE incoming request
	 * 
	 * @param joinPoint join point used with aspect
	 * @return result of method execution
	 * @throws Throwable if an error occurs
	 */
	@Around("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public Object logAroundDeleteMapping(ProceedingJoinPoint joinPoint) throws Throwable {
		
		// Log incoming request
		log.info("DELETE " + request.getRequestURL());
		long startTime = System.currentTimeMillis();
		
		try {
			// Proceed method
			return joinPoint.proceed();
		
		} finally {
	        long endtime = System.currentTimeMillis();
	        
	        // Log end incoming request
	        log.info("DELETE " + request.getRequestURL() + " in " + (endtime-startTime) + "ms");
		}
    }

}
