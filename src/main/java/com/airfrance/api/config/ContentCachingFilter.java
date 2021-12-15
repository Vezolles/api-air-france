package com.airfrance.api.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Content caching filter
 * Used with highest precedence order to be first at injection
 * @author Vezolles
 */
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(urlPatterns = "/*")
public class ContentCachingFilter extends OncePerRequestFilter {
	
	/**
	 * Same contract as for {@code doFilter}, but guaranteed to be
	 * just invoked once per request within a single request thread.
	 * See {@link #shouldNotFilterAsyncDispatch()} for details.
	 * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
	 * default ServletRequest and ServletResponse ones.
	 * 
	 * @param httpServletRequest The request to process
     * @param httpServletResponse The response associated with the request
     * @param filterChain Provides access to the next filter in the chain for this filter 
     * 				to pass the request and response to for further processing
     * @throws IOException if an I/O error occurs during this filter's processing of the request
     * @throws ServletException if the processing fails for any other reason
	 */
	@Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(new CachedBodyHttpServletRequest(httpServletRequest), httpServletResponse);
    }

}
