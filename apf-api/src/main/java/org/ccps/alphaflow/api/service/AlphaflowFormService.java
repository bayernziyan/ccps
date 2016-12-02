package org.ccps.alphaflow.api.service;
import java.util.HashMap;
import java.util.List;

import org.ccps.alphaflow.api.common.ServiceExcuteResult;
import org.ccps.alphaflow.api.pojo.*;

import net.sf.json.JSONArray;
public interface AlphaflowFormService {
	
	 public List<AlphaflowFormItem> getFormItemTypeList(Integer formId);
	 
	 public List<AlphaflowFormItem> getFormGridItemTypeList(Integer formId,Integer itemId);
	 
	 public Integer getAlphaflowItemIdByItemDef(String itemdef);
	/**
	 * 给普通表单组件赋值
	 * @param wfId
	 * @param fieldId
	 * @param value
	 * @throws Exception
	 */
	public void setNormalItemValue(Integer wfId,String fieldId,String value) throws Exception;
	/**
	 * 给组件值为基础数据的组件赋值,code传入为基础数据备注中的编号，或数据名称
	 * @param wfId
	 * @param fieldId
	 * @param code
	 * @param formItem
	 * @throws Exception
	 */
	public void setNormalItemValueCode(Integer wfId,String fieldId,String code,AlphaflowFormItem formItem) throws Exception;
	/*//清理复选框组件数据
	void cleanCheckBoxItems(Integer formDataId,Integer itemId) throws Exception;*/
	/**
	 * 给表单复选框组件赋值 values  用“,”分隔
	 * @param formDataId
	 * @param itemId
	 * @param values
	 * @throws Exception
	 */
	public void setCheckBoxItemValue(Integer formDataId,Integer itemId,String values) throws Exception;
	/**
	 * 给表单复选框组件赋值  codes 传入为基础数据备注中的编号，或数据名称 用“,”分隔
	 * @param formDataId
	 * @param itemId
	 * @param codes
	 * @param formItem
	 * @throws Exception
	 */
	public void setCheckBoxItemValueCode(Integer formDataId,Integer itemId,String codes,AlphaflowFormItem formItem) throws Exception;
	/*//清理数据方阵数据
	public void cleanGridRowValue(Integer formDataId,Integer itemId) throws Exception;*/
	/**
	 * 给表单数据方阵赋值
	 * @param formDataId
	 * @param formId
	 * @param itemId
	 * @param rows
	 * @throws Exception
	 */
	public void setGridRowValue(Integer formDataId,Integer formId,Integer itemId,List<List<AlphaflowGridItemParam>> rows) throws Exception;
	
	/**
	 * 获取表单组件值
	 * @param formDataId
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public String getAlphaflowItemData(Integer formDataId,Integer itemId) throws Exception;
	
	/**
	 * 获取表单数据方阵 数据
	 * @param formDataId
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public JSONArray getAlphaflowGridData(Integer formDataId,Integer itemId,HashMap<String,AlphaflowFormItem> colTypeMap) throws Exception; 
	
	/**
	 * 通过自定义标识 itemdef
	 * 获取流程表单数据
	 * @param wfId
	 * @param defarr
	 * @return
	 * @throws Exception
	 */
	public ServiceExcuteResult getAlphaflowInstanceFormDataByItemDef(Integer wfId,JSONArray defarr) throws Exception;
	/**
	 * 流程表单组件赋值
	 * @param wfId
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public ServiceExcuteResult updateAlphaflowInstanceSetFormData(AlphaflowInstanceBean afbean, List<AlphaflowItemParam> params) throws Exception;
}
