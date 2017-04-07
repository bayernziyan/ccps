package org.ccps.security.auth2.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.ccps.common.util.Eryptogram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserService userService;
	
	@Value("${oa.systemkey}") 
	private String APPKEY;
	
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		org.ccps.security.auth2.pojo.User user = userService.getUserByName(username);
		if(user == null)
			 throw new UsernameNotFoundException("用户" + username + "不存在!");
		
		Set<GrantedAuthority> grantedAuths = obtainGrantedAuthorities(user);  
		/*Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		 authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
		// if(user.isAdmin()) {
		  authorities.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
		// }
*/		
        boolean enabled = true;  
        boolean accountNonExpired = true;  
        boolean credentialsNonExpired = true;  
        boolean accountNonLocked = true;  
  
        UserDetails userDetails = null;
		try {
			userDetails = new org.springframework.security.core.userdetails.User(  
			        username, Eryptogram.decryptOp(user.getPassword(),APPKEY), enabled, accountNonExpired,  
			        credentialsNonExpired, accountNonLocked, grantedAuths);
		} catch (Exception e) {
			e.printStackTrace();
		}  
        return userDetails;  
	}
	
	
	private Set<GrantedAuthority> obtainGrantedAuthorities(org.ccps.security.auth2.pojo.User user) {  
	        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();  
	        authSet.add(new SimpleGrantedAuthority("ROLE_USER"));
	        authSet.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
	        
	        return authSet;  
	}  
}
