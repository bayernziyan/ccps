package org.ccps.common.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
			auth.inMemoryAuthentication().withUser("econage").password("econage123")
					.roles("USER");
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
            .anyRequest().authenticated().and().csrf().disable();//.and().httpBasic()
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

			// API calls
			http
				.authorizeRequests()
	        	.antMatchers("/**") // Need to narrow this or the main WebSecurityConfigurerAdapter
	            .access("#oauth2.hasScope('trust') and (hasRole('ROLE_USER'))");
				
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
					.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT").scopes("trust");
		}

		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer)
				throws Exception {
			oauthServer.realm("api/clients").allowFormAuthenticationForClients();
		}

	}

}