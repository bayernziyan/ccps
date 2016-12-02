package org.ccps.op.api;

import org.ccps.op.api.config.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@Import(ApplicationConfiguration.class)
@EnableAutoConfiguration
public class OpApiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(OpApiApplication.class, args);
    }
    @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OpApiApplication.class);
		
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
