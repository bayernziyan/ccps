package org.ccps.alphaflow.api.service;

import javax.servlet.http.HttpServletRequest;

import org.ccps.alphaflow.api.common.ServiceExcuteResult;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface AlphaflowTaskService {
	
	/**
	 * 通过环节ID获取流程ID
	 * @param initParam
	 * @return
	 * @throws Exception
	 */
	public Integer getWfIdByTaskId(Integer taskId) throws Exception;
	/**
	 * 流程流转到下一环节 调用wtta
	 * 
	 * @param taskId
	 * @param assigners
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public ServiceExcuteResult updateAlphaflowTaskForward(Integer taskId,JSONArray assigners,String userId) throws Exception;

	/**
	 * 修改流程环节状态，经办人
	 * @param taskId
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public ServiceExcuteResult updateAlphaflowTaskStatusAndAssignee(Integer taskId,JSONObject params) throws Exception ;
	
	/**
	 * 通过taskId,获取环节taskbean
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	public ServiceExcuteResult getAlphaflowTaskBeanById(Integer taskId) throws Exception;
	
	/**
	 * 创建一个与taskid同级的环节给assignee用户
	 * @param taskId
	 * @param assignee
	 * @return
	 * @throws Exception
	 */
	public ServiceExcuteResult createAlphaflowNewAssigneeTaskDup(Integer taskId,String assignee) throws Exception;
	/**
	 * 获取待办流程列表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public ServiceExcuteResult getAlphaflowGTasks(JSONObject params) throws Exception;
}
