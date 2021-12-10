package com.airfrance.api.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration spring boot for model mmapper
 * @author Vezolles
 */
@Configuration
public class ModelMapperConfig {
	
	/**
	 * Bean declaration for model mapper
	 * @return mapper with strategy strict of mapping
	 */
	@Bean
	public ModelMapper getModelMapper() {
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		return modelMapper;
	}

}
