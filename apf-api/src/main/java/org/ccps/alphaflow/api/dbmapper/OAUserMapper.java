package org.ccps.alphaflow.api.dbmapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ccps.alphaflow.api.pojo.UserBean;
import org.ccps.common.db.repository.WhDbRepository;

@WhDbRepository
public interface OAUserMapper {
	
	public UserBean getUserBeanByUserId(@Param(value="userId") String userId) throws SQLException;
	public UserBean getUserBeanByReqId(@Param(value="userReqId") Integer userReqId) throws SQLException;
	/**
	 * 获取其他小号用户列表
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public List<UserBean> getMyOtherWorkspaceUsersBeanByUserId(@Param(value = "userId")  String userId) throws SQLException;
}
