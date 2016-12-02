package org.ccps.alphaflow.api.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ccps.alphaflow.api.pojo.UserBean;
import org.springframework.web.multipart.MultipartHttpServletRequest;


public interface OAUserService {
	
	/**
	 * 通过userId 获取 myws_id
	 * @param initParam
	 * @return
	 * @throws Exception
	 */
	public UserBean getUserBeanByUserId(String userId) throws Exception;
	
	/**
	 * 通过userId 获取所有小号的userbean 列表
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<UserBean> getMyOtherUserBeanList(String userId) throws Exception;
	
	/**
	 * 获取用户所有小号的 ReqIds
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String getMyOtherWorksapceIdStr(List<UserBean> ulist) throws Exception;
	
	/**
	 * 获取用户所有小号的 userId
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String getMyOtherUserIdStr(List<UserBean> ulist) throws Exception;
	/**
	 * 获取用户所有小号的 userSkey
	 * @param ulist
	 * @return
	 * @throws Exception
	 */
	public String getMyOtherUserSkeyStr(List<UserBean> ulist) throws Exception;
	
}
