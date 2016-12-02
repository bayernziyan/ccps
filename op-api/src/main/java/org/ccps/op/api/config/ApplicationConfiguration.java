package org.ccps.op.api.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration 
@Import({ //org.ccps.common.navigation.FallbackNavigationConfiguration.class,
	org.ccps.common.oauth2.SecurityConfig.class, 
	org.ccps.common.db.MyBatisConfig.class,org.ccps.common.db.MultipleDataSourceAspectAdvice.class,
	ControllerConfiguration.class
})
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {
	
	/**
	 * This bean gets picked up automatically by {@link ThymeleafAutoConfiguration}.
	 */

	
	
	

}
