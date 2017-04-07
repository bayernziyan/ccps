package org.ccps.security.auth2.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;

import org.ccps.security.auth2.controller.AccessConfirmationController;
import org.ccps.security.auth2.controller.AdminController;
import org.ccps.security.auth2.oauth.MyUserApprovalHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public ContentNegotiatingViewResolver contentViewResolver() throws Exception {
	/*	ContentNegotiationManagerFactoryBean contentNegotiationManager = new ContentNegotiationManagerFactoryBean();
		contentNegotiationManager.addMediaType("json", MediaType.APPLICATION_JSON);*/

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");

		MappingJackson2JsonView defaultView = new MappingJackson2JsonView();
		defaultView.setExtractValueFromSingleKeyModel(true);

		ContentNegotiatingViewResolver contentViewResolver = new ContentNegotiatingViewResolver();
		contentViewResolver.setViewResolvers(Arrays.<ViewResolver> asList(viewResolver));
		contentViewResolver.setDefaultViews(Arrays.<View> asList(defaultView));
		return contentViewResolver;
	}



	// N.B. the @Qualifier here should not be necessary (gh-298) but lots of users report needing it.
	@Bean
	public AdminController adminController(TokenStore tokenStore,
			@Qualifier("consumerTokenServices") ConsumerTokenServices tokenServices,
			MyUserApprovalHandler userApprovalHandler) {
		AdminController adminController = new AdminController();
		adminController.setTokenStore(tokenStore);
		adminController.setTokenServices(tokenServices);
		adminController.setUserApprovalHandler(userApprovalHandler);
		return adminController;
	}
	@Bean
	public AccessConfirmationController accessConfirmationController(ClientDetailsService clientDetailsService,
			ApprovalStore approvalStore) {
		AccessConfirmationController accessConfirmationController = new AccessConfirmationController();
		accessConfirmationController.setClientDetailsService(clientDetailsService);
		accessConfirmationController.setApprovalStore(approvalStore);
		return accessConfirmationController;
	}
	@Bean
	public FilterRegistrationBean springSecurityFilterChainRegistrationBean(@Qualifier("springSecurityFilterChain") Filter filter) {
	    if(!(filter instanceof FilterChainProxy)) {
	        throw new IllegalStateException("Incorrect type " + filter);
	    }
	    FilterRegistrationBean bean = new FilterRegistrationBean();
	    bean.setFilter(filter);
	    bean.setEnabled(false);
	    List<String> path = new ArrayList<String>();
	    path.add("/res/*");
	    bean.setUrlPatterns(path);
	    return bean;
	} 
	//org.springframework.web.filter.DelegatingFilterProxy
	//SecurityContextPersistenceFilter
	/*@Bean  
    public FilterRegistrationBean  securityContextFilterRegistrationBean() {  
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();  
        SecurityContextPersistenceFilter filter = new SecurityContextPersistenceFilter();  
        registrationBean.setFilter(filter);  
        List<String> urlPatterns = new ArrayList<String>();  
        urlPatterns.add("/*");  
        registrationBean.setUrlPatterns(urlPatterns);  
        return registrationBean;  
    } */
	@Bean  
    public FilterRegistrationBean   OAuth2AuthenticationFilterRegistrationBean() {  
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();  
        OAuth2AuthenticationProcessingFilter filter = new OAuth2AuthenticationProcessingFilter();  
        registrationBean.setFilter(filter);  
        List<String> urlPatterns = new ArrayList<String>();  
        urlPatterns.add("/res/*");  
        registrationBean.setUrlPatterns(urlPatterns);  
        return registrationBean;  
    }  
	//org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter 
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
