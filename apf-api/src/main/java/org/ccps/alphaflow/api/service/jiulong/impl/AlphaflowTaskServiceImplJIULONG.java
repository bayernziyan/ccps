package org.ccps.alphaflow.api.service.jiulong.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ccps.alphaflow.api.common.JsonUtils;
import org.ccps.alphaflow.api.common.RestException;
import org.ccps.alphaflow.api.common.ServiceExcuteResult;
import org.ccps.alphaflow.api.dbmapper.*;
import org.ccps.alphaflow.api.dbmapper.ata.AlphaflowTaskMapperATA;
import org.ccps.alphaflow.api.pojo.AlphaflowFormItem;
import org.ccps.alphaflow.api.pojo.AlphaflowInstanceBean;
import org.ccps.alphaflow.api.pojo.UserBean;
import org.ccps.alphaflow.api.service.jiulong.AlphaflowTaskServiceJIULONG;
import org.ccps.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class AlphaflowTaskServiceImplJIULONG implements AlphaflowTaskServiceJIULONG{
	
	@Autowired
	private AlphaflowInstanceMapper alphaflowMapper;
	@Autowired
	private AlphaflowFormMapper alphaflowFormMapper;
	@Autowired
	private AlphaflowTaskMapper alphaflowTaskMapper;
	@Autowired
	private AlphaflowTaskMapperATA alphaflowTaskMapperJIULONG;
	@Autowired
	private OAUserMapper oaUserMapper;


	@Override
	public ServiceExcuteResult updateAlphaflowTaskForward(Integer taskId, JSONArray assigners, String userId) throws Exception {
		System.out.println("ata   ================");
		Integer wfId = alphaflowTaskMapper.getWfIdByTaskId(taskId);
		if(wfId == null||wfId == 0 ){throw new RestException(30002);}
		try{
			AlphaflowInstanceBean afinsbean =  alphaflowMapper.getAlphaflowInstanceByWfId(wfId);
			//System.out.println(assignee+"####   ++++++++");
			UserBean ubean =  oaUserMapper.getUserBeanByUserId(userId);
			Integer assigneeId = ubean!=null?ubean.getMyWsId():0;
			if(assigneeId==null||assigneeId == 0){
				throw new RestException(101);				
			}
			//设置下一环节经办人
			if(assigners!=null&&assigners.size()>0){
				for(Object obj : assigners ){
					JSONObject assobj = (JSONObject)obj;
					String itemDef = assobj.getString("itemdef");
					if(StringUtil.isBlank(itemDef))continue;
					String assignerstr = assobj.getString("users");
					if(StringUtil.isBlank(assignerstr))continue;
					String[] users = assignerstr.split(",");
					String assigner = "";
					for(String user : users){
						UserBean assbean = oaUserMapper.getUserBeanByUserId(user);
						if(assbean==null)continue;
						if(!StringUtil.isBlank(assigner))assigner += "|";
						assigner += assbean.getUserFullName();
					}
					if(StringUtil.isBlank(assigner))continue;					
					
					
					AlphaflowFormItem formItembean = alphaflowFormMapper.getAlphaflowItemBeanByItemDef(itemDef,afinsbean.getFormId());
					if(formItembean==null){
						throw new RestException(20001);
					}
					alphaflowFormMapper.setNormalItemValueOriginal(wfId,afinsbean.getFormDataId(), formItembean.getId(), assigner, taskId,userId,ubean.getUserName(),"");
					
					Integer actgroupid = formItembean.getAction_group_id();
					if(actgroupid==null||actgroupid==0) continue;
					HashMap<String,Object> map = new HashMap<String,Object>();
					map.put("i_wfId",wfId);
					map.put("i_actionGroupId", actgroupid);
					map.put("i_assigners", assigner);
					map.put("i_itemId", formItembean.getId());
					alphaflowTaskMapper.alphaflowSetTask(map);
				}
			}
			
			
			//环节驱动
			AlphaflowInstanceBean afbean = alphaflowMapper.getAlphaflowInstanceByWfId(wfId);
			Map<String,Object> map = new HashMap<String ,Object>();
			map.put("i_task_id", taskId);
			map.put("i_wf_id", wfId);
			map.put("i_form_id", afbean.getFormId());
			map.put("i_form_data_id", afbean.getFormDataId());
			map.put("i_sender_id", assigneeId);
			map.put("i_user_id1", userId);
			map.put("i_user_id2",userId);
			
			alphaflowTaskMapperJIULONG.alphaflowTaskForward(map);
			List<Integer> currTaskIds = alphaflowTaskMapper.getCurrentTaskIdsByWfId(wfId);
			JSONObject reobj = new JSONObject();
			reobj.put("fromTaskId", taskId);
			reobj.put("wfId", wfId);
			reobj.put("currTasks", JsonUtils.objectToJson(currTaskIds));
			reobj.put("msg", "驱动成功");
			return new ServiceExcuteResult().setResult(reobj);
		}catch(Exception e){
			e.printStackTrace();
			throw new RestException(e);
			
		}		
		
	}
	
}
