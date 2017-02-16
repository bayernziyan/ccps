package org.ccps.organization.api.dbmapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.ccps.common.db.repository.WhDbRepository;
import org.ccps.organization.api.pojo.User;
@WhDbRepository
public interface UserMapper  {
	
	//@Param("i_dkeycolumn") String dkeycolumn,@Param("i_ukeycolumn") String ukeycolumn,@Param("i_user") User user,@Param("o_result") String result
	public void addUser(Map<String,Object> map) throws SQLException;
	
	public void updateUser(Map<String,Object> map) throws SQLException;
	
	public void deleteUser(Map<String,Object> map) throws SQLException;

	public User selectUserDetailByCode(@Param("i_dkeycolumn") String dkeycolumn,@Param("i_ukeycolumn") String ukeycolumn,@Param("i_usercode") String userCode) throws SQLException;

	public List<User> selectValidUserDetailListByRoot(@Param("i_dkeycolumn") String dkeycolumn,@Param("i_ukeycolumn") String ukeycolumn,@Param("i_rootcode") String rootCode) throws SQLException;

	//@Param("i_dkeycolumn") String dkeycolumn,@Param("i_ukeycolumn") String ukeycolumn,@Param("i_searchexample") 
	public List<User> selectUserDetailListBySearchWapper(Map<String,Object> map) throws SQLException;
}
