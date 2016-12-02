package org.ccps.alphaflow.api.service.hongqt;
import java.util.List;

import org.ccps.alphaflow.api.pojo.*;

public interface AlphaflowFormServiceHONGQT {
	
	 public List<AlphaflowFormItem> getFormItemTypeList(Integer formId);
	 
	 public List<AlphaflowFormItem> getFormGridItemTypeList(Integer formId,Integer itemId);
	 
	
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
	 * 给表单复选框组件赋值  codes 传入为基础数据备注中的编号，或数据名称 用“,”分隔
	 * @param formDataId
	 * @param itemId
	 * @param codes
	 * @param formItem
	 * @throws Exception
	 */
	public void setCheckBoxItemValueCode(Integer formDataId,Integer itemId,String codes,AlphaflowFormItem formItem) throws Exception;
	
	/**
	 * 给组件值为基础数据的组件赋值,code传入为基础数据备注中的编号，或数据名称
	 * @param wfId
	 * @param fieldId
	 * @param code
	 * @param formItem
	 * @throws Exception
	 */
	public void setNormalItemValueCode(Integer wfId,String fieldId,String code,AlphaflowFormItem formItem) throws Exception;
}
