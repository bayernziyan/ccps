package org.ccps.alphaflow.api.dbmapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.ccps.alphaflow.api.pojo.AlphaflowFormItem;
import org.ccps.alphaflow.api.pojo.SearchListExample;
import org.ccps.common.db.WhDbMapper;

import net.sf.json.JSONObject;

public interface AlphaflowFormMapper extends WhDbMapper {	

	public List<AlphaflowFormItem> getFormItemTypeList(Integer formId) throws SQLException;
	
	public List<AlphaflowFormItem> getFormGridItemTypeList(@Param("formId") Integer formId,@Param("parentId") Integer parentId) throws SQLException;	
	
	public void setNormalItemValue(@Param("wfId") Integer wfId, @Param("fieldId") String fieldId,@Param("value") String value,@Param("createUser") String createUser ) throws SQLException;
	
	public void setNormalItemValueOriginal(@Param("wfId") Integer wfId,@Param("formDataId") Integer formDataId,@Param("itemId") Integer itemId,@Param("value") String value,@Param("taskId") Integer taskId,@Param("userId") String userId,@Param("userName") String userName,@Param("multiAssigneeStr") String multiAssigneeStr) throws SQLException;
	
	/**
	 * 通过流程实例ID获取formId
	 * @return
	 */
	public Integer getAlphaflowFormIdByWfId(@Param("wfId") Integer wfId) throws SQLException;
	
	/**
	 * 通过表单组件标识获取组件ID
	 * @return
	 */
	public Integer getAlphaflowItemIdByItemDef(@Param("itemDef") String itemDef) throws SQLException;
	/**
	 * 通过表单组件标识获取组件action_group_id 
	 * @param itemDef
	 * @return
	 * @throws SQLException
	 */
	public AlphaflowFormItem getAlphaflowItemBeanByItemDef(@Param("itemDef") String itemDef,@Param("formId") Integer formId) throws SQLException;
	/**
	 * 通过itemid获取表单组件值
	 * @param formDataId
	 * @param itemId
	 * @return
	 * @throws SQLException
	 */
	public List<String> getAlphaflowItemData(@Param("formDataId") Integer formDataId,@Param("itemId") Integer itemId) throws SQLException;
	
	
	public List<Map> getAlphaflowGridRowData(SearchListExample example) throws SQLException;
	
	public int addItemValue(@Param("formDataId") Integer formDataId,@Param("itemId") Integer itemId,@Param("value") String value) throws SQLException;
	
	public int deleteItemValue(@Param("formDataId") Integer formDataId,@Param("itemId") Integer itemId) throws SQLException;
	
	public int deleteGridValue(@Param("formDataId") Integer formDataId,@Param("itemId") Integer itemId) throws SQLException;
	
	public int setGridValue(Map param) throws SQLException;
}
