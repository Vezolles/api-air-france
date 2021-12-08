package com.airfrance.api.config;

import java.nio.charset.StandardCharsets;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ResourceConfig {
	
	@Bean
	public MessageSource getMessageSource() {
		
		ReloadableResourceBundleMessageSource messageRessource = new ReloadableResourceBundleMessageSource();
		messageRessource.setBasenames("classpath:locale/messages");
		messageRessource.setDefaultEncoding(StandardCharsets.UTF_8.name());
		messageRessource.setAlwaysUseMessageFormat(Boolean.TRUE);
        
        return messageRessource;
	}
	
	@Bean
	public LocalValidatorFactoryBean getValidator() {
		
	    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	    bean.setValidationMessageSource(getMessageSource());
	    
	    return bean;
	}

}
