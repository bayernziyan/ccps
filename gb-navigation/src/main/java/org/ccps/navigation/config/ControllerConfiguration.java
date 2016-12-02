package org.ccps.navigation.config;

import org.ccps.navigation.controller.NavigationController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class ControllerConfiguration {
	
	@Value("${api.url.base}")
	private String apiBaseUrl;
	
	
	@Bean
	public NavigationController navigationController(){
		return new NavigationController(apiBaseUrl);
	}

}
