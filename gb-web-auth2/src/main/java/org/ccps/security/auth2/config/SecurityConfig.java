package org.ccps.security.auth2.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ccps.security.auth2.oauth.MyUserApprovalHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CompositeFilter;


@Configuration
public class SecurityConfig {

	
	/*@Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("127.0.0.1");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setPassword("");
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setPoolConfig(new JedisPoolConfig());
        
        return jedisConnectionFactory;
    }*/
	
	
	@Configuration
	@EnableWebSecurity
	protected static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Autowired
		private UserDetailsService userDetailsService; 
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

		@Bean(name = "authenticationManager")
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
		       web.ignoring().antMatchers("/oauth/check_token");
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {

			// @formatter:off
//			http
//            	.anonymous().disable()..anyRequest().requiresSecure();

			//http.anonymous().and().httpBasic();
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
			//.and()
				;
			// @formatter:on
			
			 AuthenticationEntryPoint aep = new AuthenticationEntryPoint() {

			        @Override
			        public void commence(HttpServletRequest request,
			                HttpServletResponse response,
			                AuthenticationException authException) throws IOException,
			                ServletException {
			            response.sendRedirect("/login");
			        }
			    };

			    http.exceptionHandling()
			            .authenticationEntryPoint(aep);

		}
		
		@Override
		protected UserDetailsService userDetailsService() {
		    return userDetailsService;
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

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfig extends ResourceServerConfigurerAdapter {
		@Autowired
		private UserDetailsService userDetailsService; 
		

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			resources.resourceId("oauth2").stateless(false);
		}

		
		@Override
		public void configure(HttpSecurity http) throws Exception {

			// API calls
			http
			// Since we want the protected resources to be accessible in the UI as well we need 
			// session creation to be allowed (it's disabled by default in 2.0.6)
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
		.and()
		  	.authorizeRequests() .regexMatchers(HttpMethod.GET,"/oauth/token").permitAll()
            .regexMatchers(HttpMethod.POST,"/oauth/token").permitAll()//.regexMatchers("**/list/[a-z0-9]*") // Need to narrow this or the main WebSecurityConfigurerAdapter
	           ;
				
			// @formatter:on
		}
		public UserDetailsService getUserService() {
			return userDetailsService;
		}
	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfig extends
			AuthorizationServerConfigurerAdapter {

		/*@Autowired
		private RedisConnectionFactory redisConnectionFactory;*/
		
		@Autowired
		private ClientDetailsService clientDetailsService;

		@Autowired
		private AuthenticationManager authenticationManager;

		@Bean
		public TokenStore tokenStore() {
			return new InMemoryTokenStore();// new RedisTokenStore(redisConnectionFactory);
		}

		@Bean
		public ApprovalStore approvalStore() {
			TokenApprovalStore store = new TokenApprovalStore();
			store.setTokenStore(tokenStore());
			return store;
		}

		@Bean
		public UserApprovalHandler userApprovalHandler() throws Exception {
			MyUserApprovalHandler handler = new MyUserApprovalHandler();
			handler.setApprovalStore(approvalStore());
			handler.setClientDetailsService(clientDetailsService);
			handler.setRequestFactory(new DefaultOAuth2RequestFactory(
					clientDetailsService));
			handler.setUseApprovalStore(true);
			return handler;
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints)
				throws Exception {
			endpoints.tokenStore(tokenStore())
					.userApprovalHandler(userApprovalHandler())
					.authenticationManager(authenticationManager);
			
			
			
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.inMemory()
					.withClient("api-client")
					.secret("secret").resourceIds("oauth2")
					.authorizedGrantTypes("password", "authorization_code",
							"refresh_token", "client_credentials").autoApprove(true)
					.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT").scopes("read","write","trust").accessTokenValiditySeconds(1800);
			
		
		}

		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer)
				throws Exception {
			oauthServer.realm("api/clients").allowFormAuthenticationForClients();
		}

	}
	
}