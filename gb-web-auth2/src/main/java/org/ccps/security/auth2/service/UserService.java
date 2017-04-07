package org.ccps.security.auth2.service;

import org.ccps.security.auth2.pojo.User;

public interface UserService {
	public User getUserByName(String name);
}
