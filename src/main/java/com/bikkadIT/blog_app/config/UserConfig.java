package com.bikkadIT.blog_app.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}