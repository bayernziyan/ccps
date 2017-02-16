package org.ccps.organization.api.config;

import java.util.Map;

import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.templateresolver.UrlTemplateResolver;


@Configuration 
@Import({org.ccps.common.oauth2.SecurityConfig.class, 
	org.ccps.common.db.MyBatisConfig.class,org.ccps.common.db.MultipleDataSourceAspectAdvice.class,
	ControllerConfiguration.class	
})
@EnableWebMvc
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {
	
	
	/**
	 * This bean gets picked up automatically by {@link ThymeleafAutoConfiguration}.
	 */
	@Bean
	public UrlTemplateResolver urlTemplateResolver(){
		UrlTemplateResolver urlTemplateResolver = new UrlTemplateResolver();
		urlTemplateResolver.setOrder(20);
		return urlTemplateResolver;
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		super.addViewControllers(registry);
		registry.addViewController("/").setViewName("redirect:/");
	}
	
	
}
