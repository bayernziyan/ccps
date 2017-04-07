package org.ccps.security.auth2.service.impl;

import java.sql.SQLException;

import org.ccps.security.auth2.dbmapper.AuthMapper;
import org.ccps.security.auth2.pojo.User;
import org.ccps.security.auth2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private AuthMapper authMapper;
	
	public User getUserByName(String name){
		try {
			return authMapper.getUserByName(name);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
