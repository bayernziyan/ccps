package org.ccps.alphaflow.api.service.impl;

import java.util.List;

import org.ccps.alphaflow.api.dbmapper.*;
import org.ccps.alphaflow.api.pojo.UserBean;
import org.ccps.alphaflow.api.service.OAUserService;
import org.ccps.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OAUserServiceImpl implements OAUserService {

	@Autowired
	private OAUserMapper oAUserMapper;
	@Override
	public UserBean getUserBeanByUserId(String userId) throws Exception {
		if(StringUtil.isBlank(userId))return null;
		return oAUserMapper.getUserBeanByUserId(userId);
	}
	@Override
	public List<UserBean> getMyOtherUserBeanList(String userId) throws Exception {
		 return oAUserMapper.getMyOtherWorkspaceUsersBeanByUserId(userId);		
	}
	
	@Override
	public String getMyOtherWorksapceIdStr(List<UserBean> ulist) throws Exception {
		if(ulist==null||ulist.size()==0)return "";
		StringBuilder sb = new StringBuilder();
		for(UserBean ubean : ulist){
			if(sb.length()>0)sb.append(",");
			sb.append(ubean.getMyWsId());
		}		
		return sb.toString();
	}
	/**
	 * SessionFacade.setMyUserAccountStr
	 */
	@Override
	public String getMyOtherUserIdStr(List<UserBean> ulist) throws Exception {
		if(ulist==null||ulist.size()==0)return "";
		StringBuilder sb = new StringBuilder();
		for(UserBean ubean : ulist){
			if(sb.length()>0)sb.append(",");
			sb.append(ubean.getUserId());
		}		
		return sb.toString();
	}
	/**
	 * SessionFacade.setMyUserIdStr
	 */
	@Override
	public String getMyOtherUserSkeyStr(List<UserBean> ulist) throws Exception {
		if(ulist==null||ulist.size()==0)return "";
		StringBuilder sb = new StringBuilder();
		for(UserBean ubean : ulist){
			if(sb.length()>0)sb.append(",");
			sb.append(ubean.getUserSkey());
		}		
		return sb.toString();
	}

}
