package org.ccps.alphaflow.api.dbmapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.ccps.alphaflow.api.pojo.AlphaflowTaskBean;
import org.ccps.common.db.WhDbMapper;

import net.sf.json.JSONObject;

public interface AlphaflowTaskMapper extends WhDbMapper {	
	
	public Integer getWfIdByTaskId(@Param(value = "taskId") Integer taskId) throws SQLException;
	
	public List<Integer> getCurrentTaskIdsByWfId(@Param(value = "wfId") Integer wfId) throws SQLException;
	/**
	 * 执行wtta
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Integer alphaflowTaskForward(Map params) throws SQLException;
	
	/**
	 * 执行完wtta后 
	 * 执行sp_check_dealtoid_after_wtta
	 * @param params
	 * @throws SQLException
	 */
	public void alphaflowCheckDealAfterWTTA(Map params) throws SQLException ;
	
	/**
	 * 设置流程环节 sp_eccm_wfst
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Integer alphaflowSetTask(Map params) throws SQLException;
	/**
	 * 更新环节状态 update 
	 * @param statusId
	 * @param taskId
	 * @return
	 * @throws SQLException
	 */
	public Integer updateTaskStatus(@Param(value="statusId") Integer statusId,@Param(value="taskId") Integer taskId) throws SQLException;
	
	/**
	 * 更新环节状态 消息提醒 sp_eccm_air_change_task_status
	 * @param statusId
	 * @param taskId
	 * @throws SQLException
	 */
	public void alphaflowTaskChangeTaskStatus(@Param(value="taskId") Integer taskId,@Param(value="statusId") Integer statusId) throws SQLException;
	
	/**
	 * 更新环境办理人 
	 * @param assignee
	 * @param assigneeName
	 * @param taskId
	 * @throws SQLException
	 */
	public Integer updateTaskAssignee(@Param(value="assignee") Integer assignee,@Param(value="assigneeName") String assigneeName,@Param(value="assigneeType") String assigneeType, @Param(value="taskId") Integer taskId) throws SQLException;
	/**
	 * 更新环节办理人  消息提醒 sp_eccm_air_change_assignee
	 * @param params -- #{wfId,mode=IN,jdbcType=INTEGER},#{taskId,mode=IN,jdbcType=INTEGER},#{userReqId,mode=IN,jdbcType=INTEGER},#{userFullName,mode=IN,jdbcType=VARCHAR}
	 * @throws SQLException
	 */
	public void alphaflowTaskChangeAssignee(Map params) throws SQLException;
	
	/**
	 * 通过taskId获取task bean
	 * @param taskId
	 * @throws SQLException
	 */
	public AlphaflowTaskBean getAlphaflowInstanceTaskById(@Param(value="taskId") Integer taskId) throws SQLException;
	
	/**
	 * 为一个经办人复制一个新的环节
	 * 添加环节经办人   newsoft.sp_eccm_copy_new_task_assignee
	 * @param params   assignee,taskId,createUser
	 * @throws SQLException
	 */
	public void alphaflowNewAssigneeTaskDup(Map params) throws SQLException ;
	/**
	 * 获取待办流程列表 大小号
	 * @param params
	 * @return
	 */
	public List<AlphaflowTaskBean> getAlphaflowGTasksMulti(Map params) throws SQLException;
	
	/**
	 * 获取待办流程列表 
	 * @param params
	 * @return
	 */
	public List<AlphaflowTaskBean> getAlphaflowGTasksSingle(Map params) throws SQLException;

}
