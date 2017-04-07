package org.ccps.security.auth2.config;

import org.ccps.security.auth2.oauth.MyUserApprovalHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
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

@Configuration
public class OAuth2ServerConfig {

	private static final String AUTH_RESOURCE_ID = "oauth2";

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			resources.resourceId(AUTH_RESOURCE_ID)/*.stateless(false)*/;
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				// Since we want the protected resources to be accessible in the UI as well we need 
				// session creation to be allowed (it's disabled by default in 2.0.6)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
			.and()
				.requestMatchers().antMatchers("/photos/**", "/oauth/users/**","/oauth/token", "/oauth/clients/**")
			   .and().authorizeRequests().antMatchers(HttpMethod.POST,"/oauth/token").anonymous()
			.and()
				.authorizeRequests()
					.antMatchers("/oauth/me").access("#oauth2.hasScope('read')")					
					;
			
			/*http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
		.and()
		  	.authorizeRequests() .regexMatchers(HttpMethod.GET,"/oauth/token").permitAll()
            .regexMatchers(HttpMethod.POST,"/oauth/token").permitAll();*/
			
			// @formatter:on
		}

	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
		@Autowired
		private ClientDetailsService clientDetailsService;
		@Autowired
		private TokenStore tokenStore;
		
		@Autowired
		private AuthenticationManager authenticationManager;

		@Value("${tonr.redirect:http://localhost:8080/tonr2/sparklr/redirect}")
		private String tonrRedirectUri;

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

			// @formatter:off
			clients.inMemory()
			.withClient("api-client").resourceIds(AUTH_RESOURCE_ID)
			.secret("secret")
			.authorizedGrantTypes("password", "authorization_code",
					"refresh_token", "client_credentials","implicit").autoApprove(true)
			.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT").scopes("read","write","trust").accessTokenValiditySeconds(1800)
			.and().withClient("tonr")
			 			.resourceIds(AUTH_RESOURCE_ID)
			 			.authorizedGrantTypes("authorization_code", "implicit")
			 			.authorities("ROLE_CLIENT")
			 			.scopes("read", "write")
			 			.secret("secret")
			 		.and()
			 		.withClient("tonr-with-redirect")
			 			.resourceIds(AUTH_RESOURCE_ID)
			 			.authorizedGrantTypes("authorization_code", "implicit")
			 			.authorities("ROLE_CLIENT")
			 			.scopes("read", "write")
			 			.secret("secret")
			 			.redirectUris(tonrRedirectUri)
			 		.and()
		 		    .withClient("my-client-with-registered-redirect")
	 			        .resourceIds(AUTH_RESOURCE_ID)
	 			        .authorizedGrantTypes("authorization_code", "client_credentials")
	 			        .authorities("ROLE_CLIENT")
	 			        .scopes("read", "trust")
	 			        .redirectUris("http://anywhere?key=value")
		 		    .and()
	 		        .withClient("my-trusted-client")
 			            .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
 			            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT","ROLE_USER")
 			            .scopes("read", "write", "trust")
 			            .secret("secret")
 			            .accessTokenValiditySeconds(60)
		 		    .and()
	 		        .withClient("my-trusted-client-with-secret")
 			            .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
 			            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
 			            .scopes("read", "write", "trust")
 			            .secret("somesecret")
	 		        .and()
 		            .withClient("my-less-trusted-client")
			            .authorizedGrantTypes("authorization_code", "implicit")
			            .authorities("ROLE_CLIENT")
			            .scopes("read", "write", "trust")
     		        .and()
		            .withClient("my-less-trusted-autoapprove-client")
		                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
		                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
		                .scopes("read", "write", "trust").secret("somesecret")
		                .autoApprove(true);
			// @formatter:on
		}

		@Bean
		public TokenStore tokenStore() {
			return new InMemoryTokenStore();
		}
		@Bean
		public ApprovalStore approvalStore() throws Exception {
			TokenApprovalStore store = new TokenApprovalStore();
			store.setTokenStore(tokenStore);
			return store;
		}
		@Bean
		public MyUserApprovalHandler userApprovalHandler() throws Exception {
			MyUserApprovalHandler handler = new MyUserApprovalHandler();
			handler.setApprovalStore(approvalStore());
			handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
			handler.setClientDetailsService(clientDetailsService);
			handler.setUseApprovalStore(true);
			return handler;
		}
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler())
					.authenticationManager(authenticationManager);
		}

		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
			oauthServer.realm("oauth2/client").allowFormAuthenticationForClients();
		}

	}

	/*protected static class Stuff {

		@Autowired
		private ClientDetailsService clientDetailsService;

		@Autowired
		private TokenStore tokenStore;

		@Bean
		public ApprovalStore approvalStore() throws Exception {
			TokenApprovalStore store = new TokenApprovalStore();
			store.setTokenStore(tokenStore);
			return store;
		}

		@Bean
		@Lazy
		@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
		public MyUserApprovalHandler userApprovalHandler() throws Exception {
			MyUserApprovalHandler handler = new MyUserApprovalHandler();
			handler.setApprovalStore(approvalStore());
			handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
			handler.setClientDetailsService(clientDetailsService);
			handler.setUseApprovalStore(true);
			return handler;
		}
	}*/

}
