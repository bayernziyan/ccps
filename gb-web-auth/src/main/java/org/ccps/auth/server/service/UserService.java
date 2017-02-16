package org.ccps.auth.server.service;

import org.ccps.auth.server.bean.User;

public interface UserService {
	public User getUserByName(String name);
}
