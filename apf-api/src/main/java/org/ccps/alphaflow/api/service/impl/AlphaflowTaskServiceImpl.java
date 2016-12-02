package org.ccps.alphaflow.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ccps.alphaflow.api.common.AlphaflowJackson;
import org.ccps.alphaflow.api.common.AlphaflowUtil;
import org.ccps.alphaflow.api.common.JsonUtils;
import org.ccps.alphaflow.api.common.PaginationUtil;
import org.ccps.alphaflow.api.common.RestException;
import org.ccps.alphaflow.api.common.SearchUtil;
import org.ccps.alphaflow.api.common.ServiceExcuteResult;
import org.ccps.alphaflow.api.dbmapper.*;
import org.ccps.alphaflow.api.pojo.AlphaflowFormItem;
import org.ccps.alphaflow.api.pojo.AlphaflowInstanceBean;
import org.ccps.alphaflow.api.pojo.AlphaflowTaskBean;
import org.ccps.alphaflow.api.pojo.SearchListExample;
import org.ccps.alphaflow.api.pojo.UserBean;
import org.ccps.alphaflow.api.service.AlphaflowFormService;
import org.ccps.alphaflow.api.service.AlphaflowTaskService;
import org.ccps.alphaflow.api.service.OAUserService;
import org.ccps.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class AlphaflowTaskServiceImpl implements AlphaflowTaskService{
	private static final Logger logger = Logger.getLogger(AlphaflowTaskServiceImpl.class);
	@Autowired
	private AlphaflowInstanceMapper alphaflowMapper;
	@Autowired
	private AlphaflowFormMapper alphaflowFormMapper;
	@Autowired
	private AlphaflowFormService alphaflowFormService;
	@Autowired
	private OAUserService oAUserService;
	@Autowired
	private AlphaflowTaskMapper alphaflowTaskMapper;
	@Autowired
	private OAUserMapper oaUserMapper;
	@Autowired
	private AlphaflowTemplateMapper alphaflowTemplateMapper;

	
	@Override
	public Integer getWfIdByTaskId(Integer taskId) throws Exception {		
		return alphaflowTaskMapper.getWfIdByTaskId(taskId);
	}
	@Override
	public ServiceExcuteResult updateAlphaflowTaskForward(Integer taskId, JSONArray assigners, String userId) throws Exception {
		Integer wfId = alphaflowTaskMapper.getWfIdByTaskId(taskId);
		if(wfId == null||wfId == 0 ){throw new RestException(30002);}
		//判断流程状态 是否已经结束
		AlphaflowInstanceBean aibean = alphaflowMapper.getAlphaflowInstanceByWfId(wfId);
		if(aibean.getStatus_id() == AlphaflowUtil.AlphaflowInstanceStatus.CANCELLED_STATUS
				||aibean.getStatus_id() == AlphaflowUtil.AlphaflowInstanceStatus.COMPLETED_STATUS)
			throw new RestException(30005);
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
			
			alphaflowTaskMapper.alphaflowTaskForward(map);			
			//#{wfId},#{formTaskId},#{toTaskId},#{userId}
			int toTaskId = 0;
			toTaskId = map.containsKey("o_deal_to_task_id")?Integer.parseInt(String.valueOf(map.get("o_deal_to_task_id"))):0;
			logger.debug("wfId:"+wfId+",taskId:"+taskId+",deal_to_task_id:"+toTaskId);
			if(toTaskId!=0){
				map = new HashMap<String ,Object>();
				map.put("wfId", wfId);
				map.put("formTaskId", taskId);
				map.put("toTaskId", toTaskId);
				map.put("userId", userId);
				alphaflowTaskMapper.alphaflowCheckDealAfterWTTA(map);
			}
			
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
	
	
	
	@Override
	public ServiceExcuteResult updateAlphaflowTaskStatusAndAssignee(Integer taskId, JSONObject params)
			throws Exception {
		Integer wfId = alphaflowTaskMapper.getWfIdByTaskId(taskId);
		if(wfId == null||wfId == 0 ){throw new RestException(30002);}
		//判断流程状态 是否已经结束
		AlphaflowInstanceBean aibean = alphaflowMapper.getAlphaflowInstanceByWfId(wfId);
		if(aibean.getStatus_id() == AlphaflowUtil.AlphaflowInstanceStatus.CANCELLED_STATUS
				||aibean.getStatus_id() == AlphaflowUtil.AlphaflowInstanceStatus.COMPLETED_STATUS)
			throw new RestException(30005);
		// ....如果状态 不是已完成，已取消则执行下面操作....
		ServiceExcuteResult serviceExcuteResult = new ServiceExcuteResult(); 
		try{
			String status = params.containsKey("status")?params.getString("status"):null;
			String assignee = params.containsKey("assignee")?params.getString("assignee"):null;
			Integer statusId = !StringUtil.isBlank(status)?AlphaflowUtil.AlphaflowTaskStatus.getStatusId(status):0;
			
			AlphaflowTaskBean tbean = alphaflowTaskMapper.getAlphaflowInstanceTaskById(taskId);
			if(tbean==null)throw new RestException(40001);
			if(statusId != null && 0 != statusId){
				//更新环节状态
				alphaflowTaskMapper.updateTaskStatus(statusId, taskId);
				//alphaflowTaskMapper.alphaflowTaskChangeTaskStatus(taskId,statusId);
				
				JSONObject msgobj = new JSONObject();
				msgobj.put("statu", status);
				msgobj.put("msg", "状态更新成功");
				serviceExcuteResult.setParams(msgobj);
				//更新后的状态置回到当前taskbean
				tbean.setStatus_id(statusId);
				tbean.setStatus(status);
			}
			if(!StringUtil.isBlank(assignee)) {
				 List<AlphaflowTaskBean> alist =  alphaflowMapper.getAlphaflowInstanceTaskList(wfId);
				 List<AlphaflowTaskBean> lvlist = AlphaflowUtil.getAlphaflowTaskListByLevel(wfId, alist, tbean);
								 
					//更新环节经办人 ，限单人
					UserBean ubean = oAUserService.getUserBeanByUserId(assignee);
					if(ubean==null){throw new RestException(101);}
					Integer  reqid = ubean.getMyWsId();
					String  name = ubean.getFullDeptNameByLevel() + "--" + ubean.getUserName();
					//判断经办人是否已经存在
					if(AlphaflowUtil.isAssigneeContains(lvlist,reqid))throw new RestException(40002);
					if(tbean.getStatus_id()!=AlphaflowUtil.AlphaflowTaskStatus.PENDING_STATUS
							&&tbean.getStatus_id()!=AlphaflowUtil.AlphaflowTaskStatus.ASSIGNED_STATUS)
						throw new RestException(40003);
					
					alphaflowTaskMapper.updateTaskAssignee(reqid, name,"0", taskId);
					/*Map<String ,Object> mp = new HashMap<String,Object>();
					mp.put("wfId", wfId);mp.put("taskId", taskId);
					mp.put("userReqId", reqid);mp.put("userFullName", name);
					alphaflowTaskMapper.alphaflowTaskChangeAssignee(mp);*/
					
					JSONObject msgobj = new JSONObject();
					msgobj.put("assignee", assignee);
					msgobj.put("msg", "办理人更新成功");
					serviceExcuteResult.setParams(msgobj);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RestException(e);
			
		}	
		return serviceExcuteResult;
	}
	

	@Override
	public ServiceExcuteResult getAlphaflowTaskBeanById(Integer taskId) throws Exception {
		AlphaflowTaskBean atbean = alphaflowTaskMapper.getAlphaflowInstanceTaskById(taskId);
		ServiceExcuteResult serviceExcuteResult = new ServiceExcuteResult(); 
		if(atbean!=null){
			String re = AlphaflowJackson.json().filterIn("alphaflowTaskFilter", new String[]{"wfId","task_id","task_name"
                    ,"status", "task_level","assignee","assignee_name","approve_code","approve_comment"})				
						.readAsString(atbean);
			
			
			serviceExcuteResult.setResult(re);
		}else
			throw new RestException(40001);
		return serviceExcuteResult;
	}
	
	@Override
	public ServiceExcuteResult createAlphaflowNewAssigneeTaskDup(Integer taskId,String userId) throws Exception {
		AlphaflowTaskBean atbean = alphaflowTaskMapper.getAlphaflowInstanceTaskById(taskId);
		ServiceExcuteResult serviceExcuteResult = new ServiceExcuteResult(); 
		//String userId = SearchUtil.getSearchVal("assignee", param);
		if(StringUtil.isBlank(userId))throw new RestException(101);
		if(atbean!=null){
			UserBean ubean = oAUserService.getUserBeanByUserId(userId);
			if(ubean==null)throw new RestException(101);				
			//判断流程状态 是否已经结束
			AlphaflowInstanceBean aibean = alphaflowMapper.getAlphaflowInstanceByWfId(atbean.getWfId());
			if(aibean.getStatus_id() == AlphaflowUtil.AlphaflowInstanceStatus.CANCELLED_STATUS
					||aibean.getStatus_id() == AlphaflowUtil.AlphaflowInstanceStatus.COMPLETED_STATUS)
				throw new RestException(30005);
			//判断经办人是否已经存在
			List<AlphaflowTaskBean> alist =  alphaflowMapper.getAlphaflowInstanceTaskList(atbean.getWfId());
			if(AlphaflowUtil.isAssigneeContains(alist, ubean.getMyWsId()))throw new RestException(40002); 
			
			Map params = new HashMap<String, Object>();		
			params.put("assignee", ubean.getUserFullName());
			params.put("createUser", "newsoft");
			params.put("taskId", taskId);
			alphaflowTaskMapper.alphaflowNewAssigneeTaskDup(params);
			Integer new_taskid = null;
			if(params.containsKey("new_taskId"))new_taskid = Integer.parseInt(String.valueOf(params.get("new_taskId"))); 
			
			JSONObject re  = new JSONObject();
			re.put("msg", "用户环节创建成功");
			re.put("new_taskId", new_taskid);
			serviceExcuteResult.setResult(re);
		}else
			throw new RestException(40001);
		return serviceExcuteResult;
	}
	@Override
	public ServiceExcuteResult getAlphaflowGTasks(JSONObject params) throws Exception {
		String userId = SearchUtil.getSearchVal("assignee", params);
		if(StringUtil.isBlank(userId))throw new RestException(101);
		List<UserBean> other_ulist = oAUserService.getMyOtherUserBeanList(userId);
		ServiceExcuteResult svcre = new ServiceExcuteResult();
		try{
			SearchListExample sExp = new SearchListExample();
			SearchListExample.Criteria c = sExp.createCriteria();
			
			HashMap<String,Object> sqlparams = new HashMap<String,Object>();
            String group = SearchUtil.getSearchVal("group", params);
            if(!StringUtil.isBlank(group)){
            	Integer group_id = alphaflowTemplateMapper.getAlphaflowGroupIdByName(group);
            	if(group_id!=null&&group_id!=0){
            		c.addCriterion("e.group_id = ", group_id,"group");
            	}
            }
            String sub_group = SearchUtil.getSearchVal("sub_group", params);
            if(!StringUtil.isBlank(sub_group)){
            	Integer sub_group_id = alphaflowTemplateMapper.getAlphaflowGroupIdByName(sub_group);
            	if(sub_group_id!=null&&sub_group_id!=0){
            		c.addCriterion("e.sub_group_id", sub_group_id,"sub_group");
            	}
            }
            String template_id = SearchUtil.getSearchVal("template_id", params);
            if(!StringUtil.isBlank(template_id)){            
            	c.addCriterion("r.template_id", template_id,"template_id");            	
            }
            String wfname = SearchUtil.getSearchVal("wfname", params);
            if(!StringUtil.isBlank(wfname)){            
            	c.addCriterion(" (r.request_desc like '%"+wfname+"%' or r.str_att_1 like '%"+wfname+"%') ");     	
            }
            String start_date = SearchUtil.getSearchVal("start_date", params);
            if(!StringUtil.isBlank(start_date)){
            	c.addCriterion(" and e.create_date>=to_date('"+start_date+"','YYYY-MM-DD')");
            }
            String end_date = SearchUtil.getSearchVal("end_date", params);
            if(!StringUtil.isBlank(end_date)){
            	c.addCriterion(" and e.create_date<=to_date('"+end_date+"','YYYY-MM-DD') + 1 ");
            }
        	String sort = SearchUtil.getSearchVal("sort", params);
			String order = StringUtil.isBlank(SearchUtil.getSearchVal("order", params),"asc");		
			if(!StringUtil.isBlank(sort)){
				try{
					JSONArray sortarr = JSONArray.fromObject(sort);
					String orderby = PaginationUtil.getOrderBy(sortarr, order);
					sExp.setOrderByClause(orderby);
				}catch(Exception e){e.printStackTrace();}
			}		
            sqlparams.put("example", sExp);
            int pageNum = PaginationUtil.getPageNum(params);
			int pageSize = PaginationUtil.getPageSize(params);
			
			
			JSONObject reobj = new JSONObject();
			
			if(other_ulist!=null&&other_ulist.size()>0){
				//getReqTaskByMultiUserOrderBy   大小号
				String workspaceIds = oAUserService.getMyOtherWorksapceIdStr(other_ulist);
				String userSkeys = oAUserService.getMyOtherUserSkeyStr(other_ulist);
				
				sqlparams.put("userSkeys", userSkeys);
				sqlparams.put("workspaceIds", workspaceIds);
				sqlparams.put("status_1", 1);
				sqlparams.put("status_2", 3);
				sqlparams.put("status_3", 3);
				PageHelper.startPage(pageNum, pageSize);
				List<AlphaflowTaskBean> gtasks =  alphaflowTaskMapper.getAlphaflowGTasksMulti(sqlparams);
				PageInfo<AlphaflowTaskBean> pageInfo = new PageInfo<AlphaflowTaskBean>(gtasks);						
				reobj.put("total", pageInfo.getTotal());
				reobj.put("data", JsonUtils.objectToJson(gtasks));		
			}else{
				//getReqTaskOrderBy
				UserBean ubean = oAUserService.getUserBeanByUserId(userId);
				if(ubean==null)throw new RestException(101);
				Integer userReqId = ubean.getMyWsId();
				Integer userSkey = ubean.getUserSkey();
				sqlparams.put("userSkey", userSkey);
				sqlparams.put("userReqId", userReqId);
				sqlparams.put("status_1", 1);
				sqlparams.put("status_2", 3);
				sqlparams.put("status_3", 3);
				PageHelper.startPage(pageNum, pageSize);
				List<AlphaflowTaskBean> gtasks =  alphaflowTaskMapper.getAlphaflowGTasksSingle(sqlparams);
				PageInfo<AlphaflowTaskBean> pageInfo = new PageInfo<AlphaflowTaskBean>(gtasks);						
				reobj.put("total", pageInfo.getTotal());
				reobj.put("data", AlphaflowJackson.json().filterIn("alphaflowTaskFilter", new String[]{"wfId","task_id","wfName"
                        ,"group_name", "assign_date","create_date","init_id","assignee_name","assigner_name"}).readAsString(pageInfo));		
			}			
			reobj.put("start", pageNum);
			reobj.put("size",  pageSize);
			if(!StringUtil.isBlank(sort)){
				reobj.put("sort", sort);
				reobj.put("order", order);
			}
			
			svcre.setResult(reobj);
		}catch(Exception e){
			e.printStackTrace();
			throw new RestException(e);
		}	
		return svcre;
	}
	
	
	
}
