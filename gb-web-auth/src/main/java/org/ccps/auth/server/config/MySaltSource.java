package org.ccps.auth.server.config;

import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;

public class MySaltSource implements SaltSource{
	private String user;
	public static final String clientId = "app000001";
	public static final String clientSecret = "secret01212aaaa";
	public static final String clientEncodeAESKey = "4GFZqhyDVJSfTJRxoTUQsA==";
	
	
	
	@Override
	public Object getSalt(UserDetails user) {
		this.user=user.getUsername();
		return this;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	

}
