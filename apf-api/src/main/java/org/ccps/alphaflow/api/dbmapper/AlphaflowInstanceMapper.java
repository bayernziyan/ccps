package org.ccps.alphaflow.api.dbmapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.ccps.alphaflow.api.pojo.AlphaflowInstanceBean;
import org.ccps.alphaflow.api.pojo.AlphaflowTaskBean;
import org.ccps.alphaflow.api.pojo.RequestFileBean;
import org.ccps.alphaflow.api.pojo.SearchListExample;
import org.ccps.common.db.WhDbMapper;

import net.sf.json.JSONObject;

public interface AlphaflowInstanceMapper extends WhDbMapper {	
	//启动流程 返回流程reqid
	public String initWorkflow(Map params) throws SQLException;
	
	public Integer getNewAlphaflowFileId() throws SQLException;
	
	public int addAlphaflowFile(RequestFileBean bean) throws SQLException;
	
	public int getAlphaflowNumByWfId(@Param(value = "wfId") Integer wfId) throws SQLException;
	
	public AlphaflowInstanceBean getAlphaflowInstanceByWfId(@Param(value = "wfId") Integer wfId) throws SQLException ;
	
	public List<AlphaflowInstanceBean> getAlphaflowInstanceList(SearchListExample slExp) throws SQLException;
	
	/**
	 * 撤回流程
	 * @param params
	 * @throws SQLException
	 */
	public void revokeAlphaflowByWfId(Map params) throws SQLException;
	/**
	 * 终止流程
	 * @param params
	 * @throws SQLException
	 */
	public void cancelAlphaflowByWfId(Map params) throws SQLException;
	/**
	 * 流程环节跳转 wfId，taskFrom，taskTo
	 * @param params
	 * @throws SQLException
	 */
	public void jumpAlphaflowTask(Map params) throws SQLException;
	
	/**
	 * 获取流程实例 tasks
	 * @param wfId
	 * @return
	 * @throws SQLException
	 */
	public List<AlphaflowTaskBean> getAlphaflowInstanceTaskList(@Param(value = "wfId") Integer wfId) throws SQLException;
}
