package org.ccps.alphaflow.api.common;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ccps.alphaflow.api.pojo.*;
import org.ccps.alphaflow.api.service.KeyValueService;
import org.ccps.common.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AlphaflowUtil {
	
	public static JSONArray transferGridRows(List<Map> aibeans,HashMap<String,AlphaflowFormItem> colTypeMap,KeyValueService kvservice){
		if(aibeans==null)return null;
		JSONArray arr = new JSONArray();
		for(Map<String,String> rowmap : aibeans){
			JSONArray colarr = new JSONArray();
			 for (Map.Entry<String, String> entry : rowmap.entrySet()) {
				 JSONObject colobj = new  JSONObject();
				 String ind =  entry.getKey();
				 ind = ind.split("_")[1];
				 AlphaflowFormItem afitem = colTypeMap.get(ind);
				 String colval =  entry.getValue();
				 if(afitem.getSub_type_id()>=100){
					try {
						colval = getKVValue(kvservice, "Y", colval);
					} catch (Exception e) {
						e.printStackTrace();
					}
				 }	
				 colobj.put("gitemcol", ind);
				 colobj.put("gitemvalue", colval);
				 colobj.put("gitemname", afitem.getItem_name());
				 colarr.add(colobj);
			 }
			 Collections.sort(colarr, new Comparator<JSONObject>() {
				 @Override
				 public int compare(JSONObject o1, JSONObject o2) {
				 Object f1 = o1.get("gitemcol");
				 Object f2 = o2.get("gitemcol");
				 if(f1 instanceof Number && f2 instanceof Number){
				 return ((Number)f1).intValue() - ((Number)f2).intValue();
				 }else{
				 return f1.toString().compareTo(f2.toString());
				 }
				 }
			});			 
			arr.add(colarr);
		}
		return arr;
	}
	
	
	public static  String getMultiKVValue(KeyValueService kvservice,String kvflag,String codes){
		if(StringUtil.isBlank(codes))return "";
		String[] vals  = codes.split(",");
		String re = "";		
		for(String v : vals){			
			String rev = "";
			try{ rev = getKVValue(kvservice, kvflag,v);}catch(Exception e){e.printStackTrace();}
			if(StringUtil.isBlank(rev))continue;
			if(!StringUtil.isBlank(re))re += ",";
			re += rev;
		}
		return re;
	}
	
	
	public static String getKVValue(KeyValueService kvservice,String kvflag,String code) throws Exception{
		if(StringUtil.isBlank(code))return "";
		if("Y".equals(kvflag))
			return kvservice.getKeyValueDescByIdFromSys(Integer.parseInt(code));
		else
			return kvservice.getKeyValueDescByIdFromWf(Integer.parseInt(code));
	}
	
	public static boolean isAssigneeContains(List<AlphaflowTaskBean>  alist,Integer reqId){
		if(alist==null||alist.size()==0)return false;
		for(AlphaflowTaskBean tbean : alist){
			if(tbean.getAssignee() ==  reqId){
				return true;
			}
		}
		return false;
	}
	
	public static List<AlphaflowTaskBean> getAlphaflowTaskListByLevel(Integer wfId,List<AlphaflowTaskBean>  alist,AlphaflowTaskBean bean){
		if(wfId==null||bean==null||wfId==0||bean.getTask_id()==0||alist==null||alist.size()==0)return null;
		 List<AlphaflowTaskBean>  lvlist= new ArrayList<AlphaflowTaskBean> ();
		 for(AlphaflowTaskBean tbean : alist){
			 if(tbean.getTask_level() == bean.getTask_level()&&tbean.getTask_id().intValue()!=bean.getTask_id().intValue()){
				 lvlist.add(tbean);
			 }
		 }
		 return lvlist;		
	}
	
	 public static List<AlphaflowTaskBean> filterDistinctTaskList(List<AlphaflowTaskBean> list) throws Exception
      {
		 List<AlphaflowTaskBean> v = new ArrayList<AlphaflowTaskBean>();
		 Iterator i = list.iterator();
		 while (i.hasNext()) {
			 AlphaflowTaskBean tb = (AlphaflowTaskBean) i.next();
		     Iterator v_it = v.iterator();
		     boolean exists = false;
		     while (v_it.hasNext()) {
		    	 AlphaflowTaskBean check_tb = (AlphaflowTaskBean) v_it.next();
		         if (check_tb.getTask_id().intValue() == tb.getTask_id().intValue()) {
		             exists = true;
		             break;
		         }
		     }
		     if (!exists) {
		    	 tb.setStatus(AlphaflowTaskStatus.getStatusName(tb.getStatus_id()));
		         v.add(tb);
		     }
		 }
		 return v;
	}
	
	public static HashMap<String,AlphaflowFormItem> formItemTypeMap(List<AlphaflowFormItem> list){
		HashMap<String,AlphaflowFormItem> map = new HashMap<String,AlphaflowFormItem>();
		if(list==null||list.isEmpty())return map;
		Iterator<AlphaflowFormItem> it = list.iterator();
		int i=1;
		while(it.hasNext()){
			AlphaflowFormItem afitem = it.next();
			String def = afitem.getDef_field_id();
			if(!StringUtil.isBlank(def))
				map.put(def,afitem);
			else{
				map.put(String.valueOf(i++), afitem);
			}
		}
		return map;
	}
	
	public static String getAlphaflowFilePath(Integer wfId,Integer fileId,String filename,String docserver){
		StringBuilder sb =new StringBuilder(docserver);
		sb.append("/file");
		int below = (int) (wfId / 1000) * 1000;
	  	int above = ((int) (wfId / 1000) + 1) * 1000;
	  	sb.append("/"+below+"-"+above).append("/"+wfId);
	  	
	  	String dir = sb.toString();
	  	File checkDir = new File(dir);
	  	if (!checkDir.isDirectory()) checkDir.mkdirs();
	  	
	  	sb.append("/"+fileId+"_"+wfId);	  	
	  	String ext = "";
        int ldx = filename.lastIndexOf('.');
        if (ldx > 0) {
            ext = filename.substring(ldx + 1);
        }
        ext = ext.toLowerCase();
        sb.append("."+ext);
		return sb.toString();
	}
	
	
	
	public static class AlphaflowTaskStatus{
		

		  public static final int ASSIGNED_STATUS = 1;
		  public static final int OPEN_STATUS  = 2;
		  public static final int WORKING_STATUS  = 3;
		  public static final int RETURNED_STATUS  = 4;
		  public static final int OVERDUE_STATUS  = 5;
		  public static final int COMPLETED_STATUS  = 6;
		  public static final int SUSPEND_STATUS = 7; //挂起状态，常用于因其他操作而暂停流程
		  public static final int CANCELLED_STATUS  = 11;
		  public static final int STOPPED_STATUS  = 12;
		  public static final int ABN_END_STATUS  = 13;
		  public static final int UNKNOWN_STATUS = -3;

		  public static final int MAP_STATUS = 0;
		  public static final int PENDING_STATUS  = -1;
		  public static final int APPROVED_FULLY_STATUS = 20;
		  public static final int APPROVED_PARTIALLY_STATUS = 21;
		  public static final int DISAPPROVED_STATUS = 22;
		  public static final int ABSTAIN_STATUS = 23;
		  public static final int TRANSMITTED_STATUS = 15;
		  
		  public static final String PENDING_STATUS_NAME = "待审";
		  public static final String ASSIGNED_STATUS_NAME = "待办";
		  public static final String OPEN_STATUS_NAME  = "已接收";
		  public static final String WORKING_STATUS_NAME  = "办理中";
		  public static final String COMPLETED_STATUS_NAME  = "已完成";
		  public static final String SUSPEND_STATUS_NAME = "挂起";
		  public static final String STOPPED_STATUS_NAME  = "已取消";
		  public static final String CANCELLED_STATUS_NAME  = "已取消";

		  public static final String ABN_END_STATUS_NAME  = "异常结束";

		  public static final String TRANSMITTED_STATUS_NAME = "已转发";
		  
		 public static String getStatusName(int statusId) {
			    switch(statusId) {
			      case ASSIGNED_STATUS: return ASSIGNED_STATUS_NAME;
			      case OPEN_STATUS:  return OPEN_STATUS_NAME;
			      case WORKING_STATUS:  return WORKING_STATUS_NAME;
			      case SUSPEND_STATUS:  return SUSPEND_STATUS_NAME;
			      case RETURNED_STATUS:  return "returned";
			      case COMPLETED_STATUS:  return COMPLETED_STATUS_NAME;
			      case ABN_END_STATUS: return ABN_END_STATUS_NAME;
			      case OVERDUE_STATUS:  return "overdue";
			      case PENDING_STATUS:  return PENDING_STATUS_NAME;
			      case STOPPED_STATUS: return STOPPED_STATUS_NAME;
			      case CANCELLED_STATUS:  return CANCELLED_STATUS_NAME;
			      case APPROVED_FULLY_STATUS: return "approved";
			      case APPROVED_PARTIALLY_STATUS: return "approved_partially";
			      case DISAPPROVED_STATUS: return "disapprove";
			      case ABSTAIN_STATUS: return "abstain";
			      case TRANSMITTED_STATUS: return TRANSMITTED_STATUS_NAME;
			      default: return "unknown";
			    }
			  }
		 
		 public static int getStatusId(String name){
				if(name.equals( ASSIGNED_STATUS_NAME)){
				  return  ASSIGNED_STATUS;	 	}else if(name.equals( OPEN_STATUS_NAME)){
		    	  return  OPEN_STATUS;  		}else if(name.equals( WORKING_STATUS_NAME)){
		    	  return  WORKING_STATUS;  	}else if(name.equals( SUSPEND_STATUS_NAME)){
		          return  SUSPEND_STATUS;  	
		    	                            }else if(name.equals( COMPLETED_STATUS_NAME)){
		    	  return  COMPLETED_STATUS; 	}else if(name.equals( ABN_END_STATUS_NAME)){
		          return  ABN_END_STATUS; 		
		    	                           }/*else if(name.equals( PENDING_STATUS_NAME)){
		    	  return  PENDING_STATUS;  	}*/else if(name.equals( STOPPED_STATUS_NAME)){
		    	  return  STOPPED_STATUS; 		}else if(name.equals( CANCELLED_STATUS_NAME)){
		          return  CANCELLED_STATUS;  	
		    	                           	}else if(name.equals( TRANSMITTED_STATUS_NAME)){
		 }        return  TRANSMITTED_STATUS; 
		 }
	}
	
	
	public static class AlphaflowTaskType{
		
		
		
		 // task type
		  public static final int START_TYPE = 1;
		  public static final int WORKER_TYPE = 2;
		  public static final int GROUP_TYPE = 3;
		  public static final int ANALYSIS_TYPE = 4;
		  public static final int MILESTONE_TYPE = 5;
		  public static final int BREAK_TYPE = 9;//add point
		  // for form_based workflow
		  public static final int ASSIGNEE_PENDING = 6;		  //
		  public static final int SUB_PROCESS_TYPE = 7;
		  //system alert
		  public static final int SYSTEM_TYPE = 8;
		  public static final int END_TYPE = 10;
		  public static final int ABN_END_TYPE = 13;
		  // request for approval/review
		  public static final int REQUEST_TYPE = 11;
		  // serve as a helper for request for approval/review
		  public static final int AGENT_TYPE = 12;
		  public static final int WORK_PLAN_TYPE = 20;
		  // added on 08/07/07,
		  public static final int INIT_STATUS = 14;
		  // added on 09/07/04
		  public static final int TRANSMITTED_STATUS = 15;
		  
		  public static final String START_TYPE_NAME = "开始";
		  public static final String WORKER_TYPE_NAME = "个人";
		  public static final String GROUP_TYPE_NAME = "团队";
		  public static final String ANALYSIS_TYPE_NAME = "决策";
		  //public static final String BREAK_TYPE_NAME = "breakpoint";
		  public static final String MILESTONE_TYPE_NAME =  "里程碑";
		  public static final String END_TYPE_NAME = "结束";
		  public static final String ABN_END_NAME = "非正常结束";

		  public static final String ASSIGNEE_PENDING_NAME = "经办人员待定";

		  public static final String SUB_PROCESS_NAME = "子流程";

		  public static final String SYSTEM_TYPE_NAME = "系统";

		// request for approval/review
		  public static final String REQUEST_TYPE_NAME = "审批工作";
		// serve as a helper for request for approval/review
		  public static final String AGENT_TYPE_NAME = "审批子流程";

		  public static final String WORK_PLAN_NAME = "工作计划";
		  
		  public static String getTaskTypeName(int typeId) {
			    switch(typeId) {
			      case START_TYPE: return START_TYPE_NAME;
			      case WORKER_TYPE:  return WORKER_TYPE_NAME;
			      case GROUP_TYPE:  return GROUP_TYPE_NAME;
			      case ANALYSIS_TYPE:  return ANALYSIS_TYPE_NAME;
			      //case BREAK_TYPE: return BREAK_TYPE_NAME;
			      case MILESTONE_TYPE:  return MILESTONE_TYPE_NAME;
			      case END_TYPE:  return END_TYPE_NAME;
			      case ASSIGNEE_PENDING:  return ASSIGNEE_PENDING_NAME;
			      case SUB_PROCESS_TYPE: return SUB_PROCESS_NAME;
			      case SYSTEM_TYPE: return SYSTEM_TYPE_NAME;
			      case REQUEST_TYPE:   return REQUEST_TYPE_NAME;
			      case AGENT_TYPE:   return AGENT_TYPE_NAME;
			      case ABN_END_TYPE: return ABN_END_NAME;
			      case WORK_PLAN_TYPE: return WORK_PLAN_NAME;
			      default: return "unknown";
			    }
		}
		  
		  
		  public static Integer getTaskTypeId(String name) {
		      	 if(START_TYPE_NAME.equals(name)){
			      return	 START_TYPE;		}if(WORKER_TYPE_NAME.equals(name)){
			      return	 WORKER_TYPE; 	}else if(GROUP_TYPE_NAME.equals(name)){
			      return	 GROUP_TYPE;  	}else if(ANALYSIS_TYPE_NAME.equals(name)){
			      return   ANALYSIS_TYPE; 	}else if(MILESTONE_TYPE_NAME.equals(name)){
			      return	 MILESTONE_TYPE;  }else if(END_TYPE_NAME.equals(name)){
			      return  END_TYPE; 			 }else if(ASSIGNEE_PENDING_NAME.equals(name)){
			      return 	 ASSIGNEE_PENDING;}else if(SUB_PROCESS_NAME.equals(name)){
			      return	 SUB_PROCESS_TYPE;}else if(SYSTEM_TYPE_NAME.equals(name)){
			      return	 SYSTEM_TYPE; 	}else if(REQUEST_TYPE_NAME.equals(name)){
			      return	 REQUEST_TYPE;   	}else if(AGENT_TYPE_NAME.equals(name)){
			      return	 AGENT_TYPE;   	}else if(ABN_END_NAME.equals(name)){
			      return	 ABN_END_TYPE; 	}else  if(WORK_PLAN_NAME.equals(name)){
			      return	WORK_PLAN_TYPE;				
			      }
			      return -100;
		
		}
	}
	
	
	public static class AlphaflowInstanceStatus {
	
		public static final int UNKNOWN_STATUS = -1;
		public static final int PENDING_STATUS  = 0;
		public static final int INITIATED_STATUS = 1;
		public static final int WORKING_STATUS  = 2;
		public static final int COMPLETED_STATUS  = 3;
		public static final int CANCELLED_STATUS  = 11;
		public static final int ABN_COMP_STATUS  = 13;
		public static final String UNKNOWN_STATUS_NAME = "未知";
		public static final String PENDING_STATUS_NAME = "待审";
		public static final String INITIATED_STATUS_NAME = "进行中";
		public static final String WORKING_STATUS_NAME  = "办理中";
		public static final String COMPLETED_STATUS_NAME  = "已完成";
		public static final String CANCELLED_STATUS_NAME  = "已取消";
		public static final String ABN_COMP_STATUS_NAME  = "被拒绝";	
		
		public static String getStatusName(int statusId) {
		      switch(statusId) {
		        case INITIATED_STATUS: return INITIATED_STATUS_NAME;
		        case WORKING_STATUS:  return WORKING_STATUS_NAME;
		        case COMPLETED_STATUS:  return COMPLETED_STATUS_NAME;
		        case PENDING_STATUS:  return PENDING_STATUS_NAME;
		        case CANCELLED_STATUS:  return CANCELLED_STATUS_NAME;
		        case ABN_COMP_STATUS: return ABN_COMP_STATUS_NAME;
		        default: return UNKNOWN_STATUS_NAME;
		      }
		}
		
		public static int getStatusId(String name){
			if(INITIATED_STATUS_NAME.equals(name))return INITIATED_STATUS;
			else if(WORKING_STATUS_NAME.equals(name))return WORKING_STATUS;
			else if(COMPLETED_STATUS_NAME.equals(name))return COMPLETED_STATUS; 
			else if(PENDING_STATUS_NAME.equals(name))return PENDING_STATUS; 
			else if(CANCELLED_STATUS_NAME.equals(name))return CANCELLED_STATUS; 
			else if(ABN_COMP_STATUS_NAME.equals(name))return ABN_COMP_STATUS; 
			return -100;
		}
	}
}
