package org.ccps.security.auth2.dbmapper;

import java.sql.SQLException;

import org.ccps.common.db.repository.WhDbRepository;
import org.ccps.security.auth2.pojo.User;


@WhDbRepository
public interface AuthMapper {
 
	public User getUserByName(String username) throws SQLException;
}
