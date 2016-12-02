package org.ccps.alphaflow.api.controller;

import java.lang.reflect.InvocationTargetException;

import org.ccps.alphaflow.api.common.ExceptionUtil;
import org.ccps.alphaflow.api.common.RestException;
import org.ccps.alphaflow.api.common.RestResult;
import org.ccps.alphaflow.api.common.ServiceExcuteResult;
import org.ccps.alphaflow.api.service.AlphaflowTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *  流程环节接口视图
 * 
 */
@RestController
@RequestMapping("/alphaflow/task")
public class AlphaflowTaskController {
	
	@Autowired
	private AlphaflowTaskService alphaflowTaskService;
	/**
	 * 流转到下一环节，环节经办人（限单人）
	 * 
	 * @param taskId
	 * @param assignee
	 * @return
	 */
	@RequestMapping(value = "/{taskId}/forward",method = RequestMethod.POST)
	public RestResult alphaflowTaskForward(@PathVariable Integer taskId,@RequestBody JSONObject jsonParam) {	
		try {
			String userId = jsonParam.getString("userId");
			JSONArray assigners = jsonParam.containsKey("assigners")?jsonParam.getJSONArray("assigners"):null;
			ServiceExcuteResult serviceExcuteResult = alphaflowTaskService.updateAlphaflowTaskForward(taskId, assigners, userId);
			return new RestResult(0, serviceExcuteResult.getParamreStr() ,serviceExcuteResult.getResult());
		} catch (Exception e) {return ExceptionUtil.exceptionWapper(e);} 	
		
	}
	/**
	 * 更新环节状态 经办人
	 * @param taskId
	 * @param jsonParam
	 * @return
	 */
	@RequestMapping(value="/{taskId}/update" , method = RequestMethod.POST)
	public RestResult alphaflowTaskUpdate(@PathVariable Integer taskId,@RequestBody JSONObject jsonParam){
		try {
		ServiceExcuteResult serviceExcuteResult = alphaflowTaskService.updateAlphaflowTaskStatusAndAssignee(taskId, jsonParam);
		return new RestResult(0, serviceExcuteResult.getParamreStr() ,serviceExcuteResult.getResult());
		} catch (Exception e) {return ExceptionUtil.exceptionWapper(e);} 			
	}
	/**
	 * 获取环节信息
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value="/{taskId}/info",method = RequestMethod.GET)
	public RestResult alphaflowTaskInfo(@PathVariable Integer taskId){
		try {
			ServiceExcuteResult serviceExcuteResult = alphaflowTaskService.getAlphaflowTaskBeanById(taskId);
			return new RestResult(0, serviceExcuteResult.getParamreStr() ,serviceExcuteResult.getResult());
			} catch (Exception e) {return ExceptionUtil.exceptionWapper(e);} 	
	}
	/**
	 * 添加环节
	 * @param taskId
	 * @param assignee
	 * @return
	 */
	@RequestMapping(value="/{taskId}/dup",method = RequestMethod.GET)
	public RestResult alphaflowNewAssigneeTaskDup(@PathVariable Integer taskId,@RequestParam(value="assignee") String assignee){		
		try {
			
			
			ServiceExcuteResult serviceExcuteResult = alphaflowTaskService.createAlphaflowNewAssigneeTaskDup(taskId,assignee);
			return new RestResult(0, serviceExcuteResult.getParamreStr() ,serviceExcuteResult.getResult());
			} catch (Exception e) {return ExceptionUtil.exceptionWapper(e);} 	
	}
	//删除环节 先放放
	
	
	/**
	 * 查看待表流程 assigned
	 * 查看经办流程 complated
	 * @param flag
	 * @param jsonParam
	 * @return
	 */
	@RequestMapping(value="/list/{flag}",method = RequestMethod.POST )
	public RestResult alphaflowAssigneeTaskList(@PathVariable String flag,@RequestBody JSONObject jsonParam){
		try{
			if("complated".equals(flag)){
				return null;
			}else if("assigned".equals(flag)){
				ServiceExcuteResult serviceExcuteResult = alphaflowTaskService.getAlphaflowGTasks(jsonParam);
				return new RestResult(0, serviceExcuteResult.getParamreStr() ,serviceExcuteResult.getResult());
			}else{
				throw new RestException(102);
			}
		}catch (Exception e) {return ExceptionUtil.exceptionWapper(e);} 
		
	}
	
}
