package org.ccps.organization.api.service;

import java.util.List;

import org.ccps.common.db.util.SearchWapper;
import org.ccps.organization.api.common.ServiceResult;
import org.ccps.organization.api.pojo.User;

public interface UserService {
	public ServiceResult userCommonDML(User user, int flag) ;
	
	public List<ServiceResult> userBatchCommonDML(List<User> ulist,int flag);
	
	public ServiceResult selectUserDetailByCode(String userCode);
	
	public ServiceResult selectValidUserDetailListByRoot(String rootCode);
	
	public ServiceResult selectListBySearchWapper(SearchWapper<User> uSearch, boolean toPage);
}
