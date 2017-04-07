package org.ccps.security.auth2.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
//@EnableOAuth2Client
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService; 
	@Autowired
	private OAuth2ClientContext oauth2ClientContext;
	@Autowired
	private UserInfoRestTemplateFactory userInfoRestTemplateFactory;
	@Bean
	@ConditionalOnMissingBean
	public UserInfoRestTemplateFactory userInfoRestTemplateFactory() {
		return userInfoRestTemplateFactory;
	}	
	
   
    @Autowired
	protected void globalAuthentication(AuthenticationManagerBuilder auth)
			throws Exception {
		//auth.inMemoryAuthentication()
		auth.userDetailsService(userDetailsService());
	}
    @Override
	protected UserDetailsService userDetailsService() {
	    return userDetailsService;
	}
    
    
    @Bean
    @ConfigurationProperties("my.client")
    public ClientResources myClient() {
        return new ClientResources();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
    	 web.ignoring().antMatchers("/webjars/**", "/images/**", "/oauth/uncache_approvals", "/oauth/cache_approvals");
    }

    @Bean(name = "authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
   
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
    	http.userDetailsService(userDetailsService())
            .authorizeRequests()
                .antMatchers("/login.jsp").permitAll()
                //.antMatchers("/oauth/token").permitAll()
               // .anyRequest().hasRole("USER")//all others request authentication
                .and()
            .exceptionHandling()
                .accessDeniedPage("/login.jsp?authorization_error=true")
              .and() 
            // TODO: put CSRF protection back into this endpoint
            .csrf()
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
                .disable()
            .logout()
            	.logoutUrl("/logout")
                .logoutSuccessUrl("/login.jsp")
                .and()
            .formLogin()
            	.loginProcessingUrl("/login")
                .failureUrl("/login.jsp?authentication_error=true")
                .loginPage("/login.jsp")
                //.and().addFilterBefore(ssoFilter(myClient(),"/oauth/token"), BasicAuthenticationFilter.class)
                ;
        // @formatter:on
    }
    
    @Bean
	@ConditionalOnMissingBean
	@Order(2)
	public OAuth2ClientContextFilter oauth2ClientContextFilter() {
		OAuth2ClientContextFilter filter = new OAuth2ClientContextFilter();
		return filter;
	}
	
	@Bean
	@Order(3)
	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}

	
	
	protected class ClientResources {

		@NestedConfigurationProperty
		private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

		@NestedConfigurationProperty
		private ResourceServerProperties resource = new ResourceServerProperties();

		public AuthorizationCodeResourceDetails getClient() {
			return client;
		}

		public ResourceServerProperties getResource() {
			return resource;
		}
	}

}
