package org.ccps.alphaflow.api.dbmapper.hongqt;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.ccps.alphaflow.api.pojo.AlphaflowFormItem;

import net.sf.json.JSONObject;
public interface AlphaflowFormMapperHONGQT {	
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
	
	public List<AlphaflowFormItem> getFormItemTypeList(Integer formId) throws SQLException;
	
	public List<AlphaflowFormItem> getFormGridItemTypeList(@Param("formId") Integer formId,@Param("parentId") Integer parentId) throws SQLException;	
	
}
