package org.ccps.alphaflow.api.service.hongqt.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ccps.alphaflow.api.common.AlphaflowUtil;
import org.ccps.alphaflow.api.dbmapper.AlphaflowFormMapper;
import org.ccps.alphaflow.api.dbmapper.KeyValueMapper;
import org.ccps.alphaflow.api.dbmapper.hongqt.*;
import org.ccps.alphaflow.api.pojo.AlphaflowFormItem;
import org.ccps.alphaflow.api.pojo.AlphaflowGridItemParam;
import org.ccps.alphaflow.api.service.hongqt.AlphaflowFormServiceHONGQT;
import org.ccps.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 表单组件服务
 * @author Administrator
 *
 */
@Service
public class AlphaflowFormServiceImplHONGQT implements AlphaflowFormServiceHONGQT {
	@Autowired
	private AlphaflowFormMapperHONGQT alphaflowFormMapperHONGQT;
	@Autowired
	private AlphaflowFormMapper alphaflowFormMapper;
	@Autowired
	private KeyValueMapper keyValueMapper;
	
	/*@Override
	public void cleanGridRowValue(Integer formDataId, Integer itemId) throws Exception {
		// TODO Auto-generated method stub

	}*/
	/**
	 *<parameter property="i_formDataId" jdbcType="INTEGER" javaType="java.lang.Integer" mode="IN"/>
        <parameter property="i_itemId" jdbcType="INTEGER" javaType="java.lang.Integer" mode="IN"/>
		<parameter property="i_col_1" jdbcType="VARCHAR" javaType="java.lang.String" mode="IN"/> 
	 */
	@Override
	public void setGridRowValue(Integer formDataId,Integer formId, Integer itemId, List<List<AlphaflowGridItemParam>> rows) throws Exception {
		// TODO Auto-generated method stub
		alphaflowFormMapper.deleteGridValue(formDataId, itemId);		
		List<AlphaflowFormItem> formItems = getFormGridItemTypeList(formId, itemId);
		HashMap<String,AlphaflowFormItem> itemTypeMap = AlphaflowUtil.formItemTypeMap(formItems);
		
		if(rows!=null||rows.size()>0){
			for(int i=0;i<rows.size();i++){
				List<AlphaflowGridItemParam> cols = rows.get(i);
				Map<String,Object> map = new HashMap<String ,Object>();
				map.put("i_formDataId", formDataId);
				map.put("i_itemId", itemId);
				for(AlphaflowGridItemParam item : cols){
					AlphaflowFormItem afitem = itemTypeMap.containsKey(item.getGitemcol())?itemTypeMap.get(item.getGitemcol()):null;					
					if(StringUtil.isBlank(item.getGitemval())){
						if(afitem==null)continue;
						Integer vcode = keyValueMapper.getKeyValueIdByCodeFromSys(afitem.getSub_type_id(), item.getGitemvalcode());
						map.put("i_col_"+item.getGitemcol(), String.valueOf(vcode));
					}
					else
						map.put("i_col_"+item.getGitemcol(), StringUtil.isBlank(item.getGitemval(),""));
				}
				alphaflowFormMapper.setGridValue(map);
			}
		}
	}

	@Override
	public void setCheckBoxItemValueCode(Integer formDataId, Integer itemId, String codes,AlphaflowFormItem formItem) throws Exception {
		alphaflowFormMapper.deleteItemValue(formDataId, itemId);
		if(!StringUtil.isBlank(codes)){
			String[] cods = codes.split(",");			
			for(String code : cods){
				Integer ivcode = 0;
				if("Y".equals(formItem.getKey_value_flag())){
					ivcode = keyValueMapper.getKeyValueIdByCodeFromSys(Integer.parseInt(formItem.getKey_value_list()), code);
				}else{
					ivcode = keyValueMapper.getKeyValueIdByCodeFromWf(formItem.getTemplate_item_id(), code);
				}				
				alphaflowFormMapper.addItemValue(formDataId, itemId, String.valueOf(ivcode));
			}			
		}		
	}


	@Override
	public List<AlphaflowFormItem> getFormItemTypeList(Integer formId) {
		try{
			return alphaflowFormMapperHONGQT.getFormItemTypeList(formId);
		}catch(SQLException e){e.printStackTrace();}
		return null;
	}

	@Override
	public List<AlphaflowFormItem> getFormGridItemTypeList(Integer formId, Integer itemId) {
		try{
			return alphaflowFormMapperHONGQT.getFormGridItemTypeList(formId, itemId);
		}catch(SQLException e){e.printStackTrace();}
		return null;
	}

	@Override
	public void setNormalItemValueCode(Integer wfId, String fieldId, String code, AlphaflowFormItem formItem)
			throws Exception {
		Integer ivcode = 0;
		if("Y".equals(formItem.getKey_value_flag())){
			ivcode = keyValueMapper.getKeyValueIdByCodeFromSys(Integer.parseInt(formItem.getKey_value_list()), code);
		}else{
			ivcode = keyValueMapper.getKeyValueIdByCodeFromWf(formItem.getTemplate_item_id(), code);
		}
		alphaflowFormMapper.setNormalItemValue(wfId, fieldId, String.valueOf(ivcode),"newsoft");
		
	}

}
