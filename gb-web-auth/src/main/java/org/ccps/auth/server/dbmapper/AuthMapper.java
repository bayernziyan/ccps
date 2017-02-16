package org.ccps.auth.server.dbmapper;

import java.sql.SQLException;

import org.ccps.auth.server.bean.User;
import org.ccps.common.db.repository.WhDbRepository;

@WhDbRepository
public interface AuthMapper {
 
	public User getUserByName(String username) throws SQLException;
}
