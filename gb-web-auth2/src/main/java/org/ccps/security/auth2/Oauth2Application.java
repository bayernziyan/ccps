package org.ccps.security.auth2;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.ccps.security.auth2.config.MethodSecurityConfig;
import org.ccps.security.auth2.config.OAuth2ServerConfig;
import org.ccps.security.auth2.config.SecurityConfig;
import org.ccps.security.auth2.config.SecurityConfiguration;
import org.ccps.security.auth2.config.WebMvcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.mysql.jdbc.Security;
/**
 * Oauth2 Server
 *
 */
@Configuration
@Import({org.ccps.common.db.MyBatisConfig.class,org.ccps.common.db.MultipleDataSourceAspectAdvice.class,
	OAuth2ServerConfig.class,SecurityConfiguration.class,WebMvcConfig.class
	/*SecurityConfig.class*/})
@ComponentScan(basePackages={"org.ccps.security.auth2.controller","org.ccps.security.auth2.service"})
@EnableAutoConfiguration
public class Oauth2Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2Application.class, args);
    }
    @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Oauth2Application.class);
	}
   
   /* @Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		registerProxyFilter(servletContext, "springSecurityFilterChain");
		registerProxyFilter(servletContext, "oauth2ClientContextFilter");
	}*/
    
   /* 
	private void registerProxyFilter(ServletContext servletContext, String name) {
		DelegatingFilterProxy filter = new DelegatingFilterProxy(name);
		filter.setContextAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher");
		servletContext.addFilter(name, filter).addMappingForUrlPatterns(null, false, "/*");
	}*/
   /* @Bean
	protected WebApplicationContext createServletApplicationContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		return context;
	}*/
   
    
}
