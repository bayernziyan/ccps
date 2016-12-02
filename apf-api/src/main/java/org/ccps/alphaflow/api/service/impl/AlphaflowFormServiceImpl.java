package org.ccps.alphaflow.api.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ccps.alphaflow.api.common.AlphaflowUtil;
import org.ccps.alphaflow.api.common.JsonUtils;
import org.ccps.alphaflow.api.common.RestException;
import org.ccps.alphaflow.api.common.ServiceExcuteResult;
import org.ccps.alphaflow.api.dbmapper.*;
import org.ccps.alphaflow.api.pojo.*;
import org.ccps.alphaflow.api.service.AlphaflowFormService;
import org.ccps.alphaflow.api.service.KeyValueService;
import org.ccps.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 表单组件服务
 * @author Administrator
 *
 */
@Service
public class AlphaflowFormServiceImpl implements AlphaflowFormService {
	@Autowired
	private AlphaflowFormMapper alphaflowFormMapper;
	@Autowired
	private AlphaflowInstanceMapper alphaflowInstanceMapper;
	@Autowired
	private KeyValueService keyValueService;
	
	
	
	@Override
	public void setNormalItemValue(Integer wfId, String fieldId, String value) throws Exception {
		// TODO Auto-generated method stub
		alphaflowFormMapper.setNormalItemValue(wfId, fieldId, value, "newsoft");
	}

	/*@Override
	public void cleanCheckBoxItems(Integer formDataId, Integer itemId) throws Exception {
		// TODO Auto-generated method stub

	}*/

	@Override
	public void setCheckBoxItemValue(Integer formDataId, Integer itemId, String values) throws Exception {
		// TODO Auto-generated method stub
		alphaflowFormMapper.deleteItemValue(formDataId, itemId);
		if(!StringUtil.isBlank(values)){
			String[] vals = values.split(",");
			for(String val : vals){
				alphaflowFormMapper.addItemValue(formDataId, itemId, val);
			}
		}
	}

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
						Integer vcode = keyValueService.getKeyValueIdFromSys(afitem.getSub_type_id(), item.getGitemvalcode());
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
	public void setNormalItemValueCode(Integer wfId, String fieldId, String code,AlphaflowFormItem formItem) throws Exception {
		Integer ivcode = 0;
		if("Y".equals(formItem.getKey_value_flag())){
			ivcode = keyValueService.getKeyValueIdFromSys(Integer.parseInt(formItem.getKey_value_list()), code);
		}else{
			ivcode = keyValueService.getKeyValueIdFromWf(formItem.getId(), code);
		}
		setNormalItemValue(wfId, fieldId, String.valueOf(ivcode));
	}

	@Override
	public void setCheckBoxItemValueCode(Integer formDataId, Integer itemId, String codes,AlphaflowFormItem formItem) throws Exception {
		alphaflowFormMapper.deleteItemValue(formDataId, itemId);
		if(!StringUtil.isBlank(codes)){
			String[] cods = codes.split(",");			
			for(String code : cods){
				Integer ivcode = 0;
				if("Y".equals(formItem.getKey_value_flag())){
					ivcode = keyValueService.getKeyValueIdFromSys(Integer.parseInt(formItem.getKey_value_list()), code);
				}else{
					ivcode = keyValueService.getKeyValueIdFromWf(formItem.getId(), code);
				}				
				alphaflowFormMapper.addItemValue(formDataId, itemId, String.valueOf(ivcode));
			}			
		}		
	}

	@Override
	public Integer getAlphaflowItemIdByItemDef(String itemdef) {
		try{
		return alphaflowFormMapper.getAlphaflowItemIdByItemDef(itemdef);
		}catch(SQLException e){e.printStackTrace();}
		return 0;
	}

	@Override
	public List<AlphaflowFormItem> getFormItemTypeList(Integer formId) {
		try{
			return alphaflowFormMapper.getFormItemTypeList(formId);
		}catch(SQLException e){e.printStackTrace();}
		return null;
	}

	@Override
	public List<AlphaflowFormItem> getFormGridItemTypeList(Integer formId, Integer itemId) {
		try{
			return alphaflowFormMapper.getFormGridItemTypeList(formId, itemId);
		}catch(SQLException e){e.printStackTrace();}
		return null;
	}
	/**
	 * 获取表单组件值,复选框有多个值 用“,”分隔
	 * 
	 */
	@Override
	public String getAlphaflowItemData(Integer formDataId, Integer itemId) throws Exception {
		try{
			List<String> vallist = alphaflowFormMapper.getAlphaflowItemData(formDataId, itemId) ;
			if(vallist!=null&&vallist.size()>0){
				if(vallist.size()==1)return vallist.get(0);
				String val = "";
				Iterator<String> it = vallist.iterator();
				for(;it.hasNext();){
					String str = it.next();
					if(!StringUtil.isBlank(val))val += ",";
					val += str;
				}
				return val;
			}else
				return null;
		}catch(Exception e){e.printStackTrace();return null;}
		
	}
	
	@Override
	public JSONArray getAlphaflowGridData(Integer formDataId, Integer itemId,HashMap<String,AlphaflowFormItem> colTypeMap) throws Exception {
		SearchListExample slexample = new SearchListExample();
		
		Iterator<String> it = colTypeMap.keySet().iterator();
		String schcols = "";
		while(it.hasNext()){
			String n = it.next();
			if(!StringUtil.isBlank(schcols))schcols += ",";
			schcols += "col_"+n+"_value";
		}
		slexample.setSelectColumns(schcols);
		SearchListExample.Criteria c = slexample.createCriteria();		
		c.addCriterion("form_data_id = ",formDataId,"form_data_id");
		c.addCriterion("item_id = ",itemId,"item_id");
		
		List<Map> aibeans =  alphaflowFormMapper.getAlphaflowGridRowData(slexample);
		JSONArray gridarr = AlphaflowUtil.transferGridRows(aibeans, colTypeMap, keyValueService);
		
		return gridarr;
	}
	@Override
	public ServiceExcuteResult getAlphaflowInstanceFormDataByItemDef(Integer wfId, JSONArray defarr) throws Exception {	
		//判断流程状态 是否已经结束
		AlphaflowInstanceBean aibean = alphaflowInstanceMapper.getAlphaflowInstanceByWfId(wfId);
		if(aibean == null){
			throw new RestException(30002);
		}
		if(aibean.getStatus_id() == AlphaflowUtil.AlphaflowInstanceStatus.CANCELLED_STATUS
				||aibean.getStatus_id() == AlphaflowUtil.AlphaflowInstanceStatus.COMPLETED_STATUS)
			throw new RestException(30005);
		ServiceExcuteResult serviceExcuteResult = new ServiceExcuteResult();
		try{
			if(defarr==null||defarr.size()==0)return serviceExcuteResult;
			//获取组件类型
			List<AlphaflowFormItem> formItems = getFormItemTypeList(aibean.getFormId());
			HashMap<String,AlphaflowFormItem> itemTypeMap = AlphaflowUtil.formItemTypeMap(formItems);
			JSONArray itemvals = new JSONArray(); 
			for(Object defobj : defarr){
				String defstr = String.valueOf(defobj);
				AlphaflowFormItem afitem = itemTypeMap.containsKey(defstr)?itemTypeMap.get(defstr):null;
				if(afitem==null){
					JSONObject remsg = new JSONObject();
					remsg.put("itemdef", defstr);
					remsg.put("errmsg", "二次开发标识符不存在");
					serviceExcuteResult.setParams(remsg);
					continue;}
				if(afitem.getType_id()==16){
					//数据方阵
					String gridname = afitem.getItem_name();
					gridname = gridname.contains("-")?gridname.split("-")[0]:gridname;
					JSONObject itemval = new JSONObject();
					itemval.put("griddef", afitem.getDef_field_id());
					itemval.put("gridname", gridname);
					//获取数据方阵json
					List<AlphaflowFormItem> gridcols =  getFormGridItemTypeList(aibean.getFormId(), afitem.getId());
					HashMap<String,AlphaflowFormItem> colTypeMap = AlphaflowUtil.formItemTypeMap(gridcols);
					try{
					JSONArray gridarr = getAlphaflowGridData(aibean.getFormDataId(), afitem.getId(), colTypeMap);
					itemval.put("gitems", gridarr);
					itemvals.add(itemval);
					}catch(Exception e){
						JSONObject remsg = new JSONObject();
						remsg.put("itemdef", defstr);
						remsg.put("errmsg", e.getMessage());
						serviceExcuteResult.setParams(remsg);
						continue;						
					}
				}else{
					String itemstr = getAlphaflowItemData(aibean.getFormDataId(), afitem.getId());
					String cbflag = 10==afitem.getType_id()?"Y":"N";//复选框checkbox
					String kvflag = StringUtil.isBlank(afitem.getKey_value_flag(),"N");
					Integer kvcat = Integer.parseInt(StringUtil.isBlank(afitem.getKey_value_list(),"0"));
					String valstr = itemstr;
					if(kvcat!=null&&kvcat!=0){
						if("Y".equals(cbflag)){
							valstr = AlphaflowUtil.getMultiKVValue(keyValueService,kvflag,itemstr);
						}else
							valstr = AlphaflowUtil.getKVValue(keyValueService, kvflag, itemstr);
					}
					String itemname = afitem.getItem_name();
					itemname = itemname.contains("-")?itemname.split("-")[0]:itemname;
					JSONObject itemval = new JSONObject();
					itemval.put("itemdef", afitem.getDef_field_id());
					itemval.put("itemname", itemname);
					itemval.put("itemval", valstr);
					itemvals.add(itemval);
				}
			}
			
			serviceExcuteResult.setResult(itemvals);
			return serviceExcuteResult;
		}catch(Exception e){
			e.printStackTrace();
			throw new RestException(e);
		}	
	}
	
	@Override
	public ServiceExcuteResult updateAlphaflowInstanceSetFormData(AlphaflowInstanceBean afbean, List<AlphaflowItemParam> params)
			throws Exception {
		ServiceExcuteResult svcre = new ServiceExcuteResult();	
			
		try{
			//表单组件
			//ecl_request_form_item type_id 10:复选框  16:数据方阵
			//获取组件类型
			List<AlphaflowFormItem> formItems = getFormItemTypeList(afbean.getFormId());
			HashMap<String,AlphaflowFormItem> itemTypeMap = AlphaflowUtil.formItemTypeMap(formItems);
			//SP_DEVPACK_SAVE_VALUE_BY_FIELD (wf_id_in   in    number,
			// field_id_in   in    varchar2, value_in      in    varchar2, user_id_in  in varchar2) 通过二次开发标识给组件赋值
			List<AlphaflowItemParam> plist = params;
			for(AlphaflowItemParam itemp : plist){
				String itemdef = itemp.getItemdef();
				String griddef = itemp.getGriddef();
				AlphaflowFormItem iparam = itemTypeMap.containsKey(itemdef)?itemTypeMap.get(itemdef):null;
				AlphaflowFormItem gparam = itemTypeMap.containsKey(griddef)?itemTypeMap.get(griddef):null;
				if(iparam==null&&gparam==null){
					JSONObject pobj = JSONObject.fromObject(JsonUtils.objectToJson(itemp));
					pobj.put("errmsg", "二次开发标识不存在");
					svcre.setParams(pobj);
					continue;
				}				
				if(iparam!=null&&!StringUtil.isBlank(itemdef)){			
					int type = itemTypeMap.containsKey(itemdef)?iparam.getType_id():-1;
					if(type==-1||type==16)continue;
					try{
						//复选框赋值
						if(type==10){
							if(!StringUtil.isBlank(itemp.getItemval()))
								setCheckBoxItemValue(afbean.getFormDataId(), iparam.getId(), itemp.getItemval());
							else if(!StringUtil.isBlank(itemp.getItemvalcode())){
								setCheckBoxItemValueCode(afbean.getFormDataId(), iparam.getId(), itemp.getItemvalcode(), iparam);
							}
						}
						//普通组件赋值
						else{	
							if(!StringUtil.isBlank(itemp.getItemval()))
								setNormalItemValue(afbean.getWfId(), itemdef, itemp.getItemval());
							else
								setNormalItemValueCode(afbean.getWfId(), itemdef,  itemp.getItemvalcode(), iparam);
						}
					}catch(Exception e){
						JSONObject pobj = JSONObject.fromObject(JsonUtils.objectToJson(itemp));
						pobj.put("errmsg", e.getMessage());
						svcre.setParams(pobj);
					}
				}
				//数据方阵赋值
				if(gparam!=null&&!StringUtil.isBlank(griddef)){
					try{
						setGridRowValue(afbean.getFormDataId(), afbean.getFormId(),gparam.getId(), itemp.getGitems());
					}catch(Exception e){
						JSONObject pobj = JSONObject.fromObject(JsonUtils.objectToJson(itemp));
						pobj.put("errmsg", e.getMessage());
						svcre.setParams(pobj);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RestException(e);
		}
		return svcre;
	}
}
