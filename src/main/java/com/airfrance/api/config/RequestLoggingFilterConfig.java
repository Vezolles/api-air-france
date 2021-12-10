package com.airfrance.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Configuration spring boot for request logging
 * @author Vezolles
 */
@Configuration
public class RequestLoggingFilterConfig {
	
	/**
	 * Bean declaration for request logging
	 * @return request logging filter
	 */
	@Bean
    public CommonsRequestLoggingFilter logFilter() {
		
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        
        filter.setIncludeClientInfo(true);
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setIncludeHeaders(false);
        filter.setMaxPayloadLength(64000);
        
        return filter;
    }

}
