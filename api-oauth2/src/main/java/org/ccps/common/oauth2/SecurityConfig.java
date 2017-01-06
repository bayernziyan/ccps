package org.ccps.common.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

//import redis.clients.jedis.JedisPoolConfig;

@Configuration

public class SecurityConfig {

	
	/*@Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("192.168.1.43");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setPassword("");
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setPoolConfig(new JedisPoolConfig());
        return jedisConnectionFactory;
    }*/
	

	
	@Configuration
	@EnableWebMvcSecurity
	protected static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		protected void globalAuthentication(AuthenticationManagerBuilder auth)
				throws Exception {
			//auth.inMemoryAuthentication()
			auth.inMemoryAuthentication().withUser("econage").password("econage123")
			.roles("ADMIN").and().withUser("aaa").password("aaa")
			.roles("USER");;
			
		}

		@Bean(name = "authenticationManager")
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
		        web.ignoring().antMatchers("/webjars/**");
		        
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {

			// @formatter:off
//			http
//            	.anonymous().disable()..anyRequest().requiresSecure();

			//http.anonymous().and().httpBasic();
			http
			.authorizeRequests()
            .antMatchers("/mappings/**","/env/**","/health/**","/metrics/**","/trace/**","/dump/**","/beans/**","/info/**","/autoconfig/**","/configprops/**","/logfile/**","/jolokia/**").permitAll()
           /* .antMatchers("/oauth/token").permitAll()
            .antMatchers("/user/list/**","/dept/list/**")
            .access("hasRole('ROLE_USER')").anyRequest().denyAll()*/
            .and().csrf().disable()//.and().httpBasic()
			//.and()
				;
			// @formatter:on

		}

	}

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfig extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(HttpSecurity http) throws Exception {

			// @formatter:off
			/*http
            	.requiresChannel().anyRequest().requiresSecure();*/

			
			/* http
             .requestMatchers().antMatchers("/sync/**", "/oauth/users/**", "/oauth/clients/**","/me", "/oauth/token")
         .and()
             .authorizeRequests()
                 .regexMatchers(HttpMethod.GET,"/oauth/token").permitAll()
                 .regexMatchers(HttpMethod.POST,"/oauth/token").permitAll()
                 .antMatchers("/sync").access("#oauth2.hasScope('read') or #oauth2.hasScope('write')")
                 .antMatchers("/sync/**").access("#oauth2.hasScope('read') or #oauth2.hasScope('write')")
                 .antMatchers("/photos/trusted/**").access("#oauth2.hasScope('trust')")
                 .antMatchers("/photos/user/**").access("#oauth2.hasScope('trust')")
                 .antMatchers("/photos/**").access("#oauth2.hasScope('read')")
                 .regexMatchers(HttpMethod.DELETE, "/oauth/users/([^/].*?)/tokens/.*")
                     .access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('write')")
                 .regexMatchers(HttpMethod.GET, "/oauth/clients/([^/].*?)/users/.*")
                     .access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('read')")
                 .regexMatchers(HttpMethod.GET, "/oauth/clients/.*")
                     .access("#oauth2.clientHasRole('ROLE_CLIENT') and #oauth2.isClient() and #oauth2.hasScope('read')");*/
			
			// API calls
			http.anonymous().disable()
				.requestMatchers().antMatchers("/**")
				.and()
				.authorizeRequests() .regexMatchers(HttpMethod.GET,"/oauth/token").permitAll()
                .regexMatchers(HttpMethod.POST,"/oauth/token").permitAll()//.regexMatchers("**/list/[a-z0-9]*") // Need to narrow this or the main WebSecurityConfigurerAdapter
            	.antMatchers("/**/list/**","/**/detail/**")
                .access("(#oauth2.hasScope('read') and (hasRole('ROLE_USER'))) or ( hasRole('ROLE_ADMIN'))")
                .antMatchers("/**/add","/**/update","/**/delete","/alphaflow/**/create","/alphaflow/**/uploadfiles").access("(#oauth2.hasScope('write') or #oauth2.hasScope('trust') ) and (hasRole('ROLE_ADMIN'))").anyRequest().denyAll()
               // .antMatchers("/**").access("#oauth2.hasScope('trust') and (hasRole('ROLE_ADMIN'))").anyRequest().denyAll()
	        	//.and().authorizeRequests().antMatchers("**/add/**","**/update/**","**/delete/**","**/list/**") // Need to narrow this or the main WebSecurityConfigurerAdapter
	            //.access("#oauth2.hasScope('trust') and (hasRole('ROLE_ADMIN'))")
	            
	           ;
				
			// @formatter:on
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
			return  new InMemoryTokenStore();//            new RedisTokenStore(redisConnectionFactory);
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
					.secret("secret")
					.authorizedGrantTypes("password", "authorization_code",
							"refresh_token", "client_credentials")
					.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT").scopes("read","write","trust");
			
		
		}

		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer)
				throws Exception {
			oauthServer.realm("api/clients").allowFormAuthenticationForClients();
		}

	}

}