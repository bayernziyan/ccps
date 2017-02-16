package org.ccps.auth.server.service.impl;

import java.sql.SQLException;

import org.ccps.auth.server.bean.User;
import org.ccps.auth.server.dbmapper.AuthMapper;
import org.ccps.auth.server.service.UserService;
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
