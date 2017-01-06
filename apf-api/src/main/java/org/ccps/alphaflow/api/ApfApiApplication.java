package org.ccps.alphaflow.api;


import java.io.PrintStream;

import javax.servlet.MultipartConfigElement;

import org.ccps.alphaflow.api.config.ApplicationConfiguration;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@Import({ ApplicationConfiguration.class })
@EnableAutoConfiguration
//@MapperScan("org.ccps.alphaflow.api.dbmapper")
public class ApfApiApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		 SpringApplication application = new SpringApplication(ApfApiApplication.class); 
		 application.run(ApfApiApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApfApiApplication.class);
		
		/*application
        .showBanner(true)
        .parent(Global.class)
        .sources(applicationClass)
        .profiles("container")
        ;*/
	}

	
	@Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        factory.setMaxFileSize("10485760000");  
        //factory.setMaxRequestSize("40960");        
        return factory.createMultipartConfig();  
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
