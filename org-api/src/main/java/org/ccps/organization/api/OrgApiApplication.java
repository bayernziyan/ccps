package org.ccps.organization.api;



import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.ccps.organization.api.config.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@Import({ ApplicationConfiguration.class })
//@SpringBootApplication
@EnableAutoConfiguration
//@MapperScan("org.ccps.alphaflow.api.dbmapper")
public class OrgApiApplication extends SpringBootServletInitializer   {
	public static void main(String[] args) {
		 SpringApplication application = new SpringApplication(OrgApiApplication.class); 
		 application.run(OrgApiApplication.class, args);
	}

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OrgApiApplication.class);
    }
}
