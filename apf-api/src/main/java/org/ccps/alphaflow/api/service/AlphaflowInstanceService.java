package org.ccps.alphaflow.api.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ccps.alphaflow.api.common.ServiceExcuteResult;
import org.ccps.alphaflow.api.pojo.AlphaflowInitParam;
import org.ccps.alphaflow.api.pojo.AlphaflowInstanceBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface AlphaflowInstanceService {
	
	/**
	 * 启动流程
	 * @param initParam
	 * @return
	 * @throws Exception
	 */
	public ServiceExcuteResult initAlphaflow(AlphaflowInitParam initParam) throws Exception;
	/**
	 * 上传流程附件
	 * @param request
	 * @param wfId
	 * @return
	 * @throws Exception
	 */
	public ServiceExcuteResult uploadAlphaflowFiles(HttpServletRequest request,Integer wfId) throws Exception;
	/**
	 * 判断流程实例是否存在
	 * @param wfId
	 * @return
	 * @throws Exception
	 */
	public boolean isAlphaflowExisted(Integer wfId) throws Exception;
	/**
	 * 撤回流程
	 * @param wfId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public ServiceExcuteResult updateRevokeAlphaflowByWfId(Integer wfId,String userId) throws Exception;
	
	/**
	 * 获取流程实例bean
	 * @param wfId
	 * @return
	 * @throws Exception
	 */
	public AlphaflowInstanceBean getAlphaflowInstanceBeanByWfId(Integer wfId) throws Exception;
	/**
	 * 获取流程实例JSON
	 * @param wfId
	 * @return
	 * @throws Exception
	 */
	public ServiceExcuteResult getAlphaflowInstanceByWfId(Integer wfId) throws Exception ;
	/**
	 * 获取流程实例列表
	 * @return
	 * @throws Exception
	 */
	public ServiceExcuteResult getAlphaflowInstanceList(JSONObject searchParams) throws Exception ;
	
	/**
	 * 终止流程
	 * @param params
	 * @throws SQLException
	 */
	public ServiceExcuteResult updateCancelAlphaflowByWfId(Integer wfId,String userId) throws Exception;
	/**
	 * 获取流程实例 环节列表
	 * @param wfId
	 * @return
	 * @throws Exception
	 */
	public ServiceExcuteResult getAlphaflowInstanceTaskList(Integer wfId) throws Exception;
	/**
	 * 流程环节跳转
	 * @param wfId
	 * @param taskFrom
	 * @param taskTo
	 * @return
	 * @throws Exception
	 */
	public ServiceExcuteResult jumpAlphaflowInstanceTask(Integer wfId,Integer taskFrom,Integer taskTo) throws Exception;
	
	
	
	
	
	public ServiceExcuteResult testAlphaflow() throws Exception;
}
