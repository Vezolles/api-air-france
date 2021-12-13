package com.airfrance.api.config;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.airfrance.api.user.dto.UserDTO;
import com.airfrance.api.user.model.User;

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
		
		TypeMap<User, UserDTO> userToUserDTOMapping = modelMapper.createTypeMap(User.class, UserDTO.class);
		userToUserDTOMapping.addMappings(mapping -> mapping.using(DATE_TO_LOCAL_DATE_CONVERTER).map(User::getBirthdate, UserDTO::setBirthdate));
		
		TypeMap<UserDTO, User> userDTOToUserMapping = modelMapper.createTypeMap(UserDTO.class, User.class);
		userDTOToUserMapping.addMappings(mapping -> mapping.using(LOCAL_DATE_TO_DATE_CONVERTER).map(UserDTO::getBirthdate, User::setBirthdate));
		
		return modelMapper;
	}
	
	/**
	 * Static function for converting Date to LocalDate
	 * @return converter date to localdate
	 */
	private static final Converter<Date, LocalDate> DATE_TO_LOCAL_DATE_CONVERTER = mappingContext -> {
	    Date source = mappingContext.getSource();
	    return source.toInstant().atZone(ZoneOffset.UTC).toLocalDate();
	};

	/**
	 * Static function for converting LocalDate to Date
	 * @return converter localdate to date
	 */
	private static final Converter<LocalDate, Date> LOCAL_DATE_TO_DATE_CONVERTER = mappingContext -> {
	    LocalDate source = mappingContext.getSource();
	    return Date.from(source.atStartOfDay(ZoneOffset.UTC).toInstant());
	};

}
