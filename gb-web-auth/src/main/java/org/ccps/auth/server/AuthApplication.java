package org.ccps.auth.server;

import org.ccps.auth.server.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@Import({ SecurityConfig.class,org.ccps.common.db.MyBatisConfig.class,org.ccps.common.db.MultipleDataSourceAspectAdvice.class })
@ComponentScan(basePackages={"org.ccps.auth.server.controller","org.ccps.auth.server.service"})
@EnableAutoConfiguration
public class AuthApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		 SpringApplication application = new SpringApplication(AuthApplication.class); 
		 application.run(AuthApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AuthApplication.class);
		
		/*application
        .showBanner(true)
        .parent(Global.class)
        .sources(applicationClass)
        .profiles("container")
        ;*/
	}
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
	    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
	    characterEncodingFilter.setEncoding("UTF-8");
	    registrationBean.setFilter(characterEncodingFilter);
	    return registrationBean;
	}
}
