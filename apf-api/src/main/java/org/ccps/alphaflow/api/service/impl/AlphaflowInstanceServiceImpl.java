package org.ccps.alphaflow.api.service.impl;

import java.io.File;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ccps.alphaflow.api.common.*;
import org.ccps.alphaflow.api.dbmapper.*;
import org.ccps.alphaflow.api.pojo.*;
import org.ccps.alphaflow.api.pojo.RequestFileBean;
import org.ccps.alphaflow.api.service.AlphaflowFormService;
import org.ccps.alphaflow.api.service.AlphaflowInstanceService;
import org.ccps.alphaflow.api.service.KeyValueService;
import org.ccps.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.druid.sql.PagerUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 流程服务
 * @author Administrator
 *
 */
@Service
public class AlphaflowInstanceServiceImpl implements AlphaflowInstanceService {

	@Autowired
	private AlphaflowInstanceMapper alphaflowInstanceMapper;
	@Autowired
	private AlphaflowFormMapper alphaflowFormMapper;
	@Autowired
	private AlphaflowFormService alphaflowFormService;
	@Autowired
	private AlphaflowTaskMapper alphaflowTaskMapper;
	@Autowired
	private AlphaflowTemplateMapper alphaflowTemplateMapper;
	@Autowired
	private OAUserMapper oaUserMapper;
	@Autowired
	private KeyValueService keyValueService;
	
	@Value("${oa.doc_server_path}")
	private String doc_server_path;
	
	@Value("${oa.alphaflow_file_invalid_suffix}")
	private String invalid_suffix;
	
	/**
	 * 启动流程
	 */
	@Override
	public ServiceExcuteResult initAlphaflow(AlphaflowInitParam initParam) throws Exception {		
		Map<String , Object> initmap = new HashMap<String , Object>();
		initmap.put("i_wfDef", initParam.getWfdef());
		initmap.put("i_initUser", initParam.getInituser());
		alphaflowInstanceMapper.initWorkflow(initmap);
		Integer reqId = 0,formId = 0,formDataId = 0;
		if(initmap.containsKey("o_reqId")){
			reqId = (Integer)initmap.get("o_reqId");			
		}
		if(initmap.containsKey("o_formId"))
			formId = (Integer)initmap.get("o_formId");
		if(initmap.containsKey("o_formDataId"))
			formDataId = (Integer)initmap.get("o_formDataId");
		if(reqId==0||formId==0){throw new RestException(30001);}
		ServiceExcuteResult svcre = new ServiceExcuteResult();
		AlphaflowInstanceBean afbean = alphaflowInstanceMapper.getAlphaflowInstanceByWfId(reqId);
		if(afbean==null){
			throw new RestException(30002);
		}
		svcre = alphaflowFormService.updateAlphaflowInstanceSetFormData(afbean, initParam.getParams());
		
		List<Integer> currTaskIds = alphaflowTaskMapper.getCurrentTaskIdsByWfId(reqId);
		JSONObject reobj = new JSONObject();
		reobj.put("wfId", reqId);
		reobj.put("currTasks", JsonUtils.objectToJson(currTaskIds));
		//返回流程编号		
		if(reqId>0)return svcre.setResult(reobj);
		return null;
	}

	/**
	 * 上传流程附件
	 */
	@Override
	public ServiceExcuteResult uploadAlphaflowFiles(HttpServletRequest request,Integer wfId) throws Exception {
		ServiceExcuteResult svcre = new ServiceExcuteResult();		
		if(!isAlphaflowExisted(wfId)){
			throw new RestException(30002);
		}	
		if(request == null){throw new RestException(100);}
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;		
		String itemId = "";
		String itemdef = multiRequest.getParameter("itemdef");
		String createUser = multiRequest.getParameter("createUser");
		if(!StringUtil.isBlank(itemdef)){
			Integer formId = alphaflowFormMapper.getAlphaflowFormIdByWfId(wfId);
			if(formId!=null&&formId!=0){
				Integer i_id = alphaflowFormService.getAlphaflowItemIdByItemDef(itemdef);
				if(i_id!=null&&i_id!=0)itemId = String.valueOf(i_id);
			}
			if(StringUtil.isBlank(itemId)){throw new RestException(20001);}
		}
		
		if(StringUtil.isBlank(createUser)){throw new RestException(30101);}
		Iterator<String> iter = multiRequest.getFileNames();  
		int i = 1;
		JSONArray rearr = new JSONArray();
        while(iter.hasNext()){  
        	JSONObject reobj = new JSONObject();
        	reobj.put("wfId", wfId);
        	if(!StringUtil.isBlank(itemId))reobj.put("itemId", itemId);
        	if(!StringUtil.isBlank(createUser))reobj.put("createUser", createUser);
            int pre = (int) System.currentTimeMillis();  
            //取得上传文件  
            MultipartFile file = multiRequest.getFile(iter.next());  
            if(file != null){  
                //取得当前上传文件的文件名称  
                String myFileName = file.getOriginalFilename();  
                reobj.put("fileName", myFileName);
                //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                if(myFileName.trim() !=""){  
                    System.out.println(myFileName); 
                    int fileId = 0;
                    fileId = alphaflowInstanceMapper.getNewAlphaflowFileId();
                    String ext = "";
                    int ldx = myFileName.lastIndexOf('.');
                    if (ldx > 0) {
                        ext = myFileName.substring(ldx + 1);
                    }
                    if(!StringUtil.isBlank(invalid_suffix)&&(","+invalid_suffix+",").equals(ext)){
                    	reobj.put("errmsg", "不支持该文件类型");
						svcre.setParams(reobj);						
						continue;
                    }
                    RequestFileBean bean = new RequestFileBean();
                    bean.setId(fileId);
                    bean.setFileName(myFileName);
                    String file_name = ldx>0?myFileName.substring(0,ldx):myFileName;
                    bean.setTitle(file_name);
                    bean.setCanDownload("Y");
                    bean.setLock_flag("N");
                    bean.setFileType("FILE");
                    bean.setParentId(0);
                    bean.setDesc("");
                    bean.setRequestId(wfId);
                   // bean.setRequestDesc();
                    bean.setType(FileTypeDef.getTypeId(ext));
					bean.setCreateUser(StringUtil.isBlank(createUser,""));
					bean.setFileLevel(1);
					bean.setDisplayFlag("Y");
					bean.setPub_flag("N");
					bean.setOrder_id(i++);
					if(!StringUtil.isBlank(itemId))
						bean.setRelated_item_id(Integer.parseInt(itemId));
					long size = file.getSize()/1024;
					bean.setFileSize(String.valueOf(size)+" KB");
					if(size>=1024){
						size = size/1024;
						bean.setFileSize(String.valueOf(size)+" MB");
					}
					try{
						int c = alphaflowInstanceMapper.addAlphaflowFile(bean);
						System.out.println("@@@@@@@@@@"+c);
	                    //定义上传路径  
	                    String path = AlphaflowUtil.getAlphaflowFilePath(wfId, fileId, myFileName, doc_server_path) ; 
	                    File localFile = new File(path);  
	                    file.transferTo(localFile);  
					}catch(Exception e){
						reobj.put("errmsg", e.getMessage());
						svcre.setParams(reobj);						
						continue;
					}
					reobj.put("fileId" , fileId);
                }               
                
            }  
            int finaltime = (int) System.currentTimeMillis();  
            reobj.put("timecost",finaltime - pre);  
            rearr.add(reobj);
        }  
        return svcre.setResult(rearr);
	}

	/**
	 * 判断流程实例是否存在
	 */
	@Override
	public boolean isAlphaflowExisted(Integer wfId) throws Exception {
		int count = 0;
		try{
		count = alphaflowInstanceMapper.getAlphaflowNumByWfId(wfId);
		}catch(SQLException e){throw new RestException(e);}
		if(count>0)return true;
		return false;
	}

	/**
	 * 撤销流程
	 */
	@Override
	public ServiceExcuteResult updateRevokeAlphaflowByWfId(Integer wfId,String userId) throws Exception {
		ServiceExcuteResult svcre = new ServiceExcuteResult();
		JSONObject remsg = new JSONObject();
		AlphaflowInstanceBean afbean = alphaflowInstanceMapper.getAlphaflowInstanceByWfId(wfId);
		if(afbean==null){
			throw new RestException(30002);
		}	
		Integer returnCode = 0;
		try{
			UserBean ubean = oaUserMapper.getUserBeanByReqId(afbean.getInitUserId());
			if(StringUtil.isBlank(userId))userId = ubean.getUserId();
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("wfId", wfId);
			map.put("userId", userId);
			map.put("returnCode", returnCode);
			alphaflowInstanceMapper.revokeAlphaflowByWfId(map);
			returnCode = (Integer) map.get("returnCode");
			if(returnCode == 1 ){
				svcre.setResult("回撤操作成功");
			}else if(returnCode == 2){
				throw new RestException(30003);
			}else if(returnCode == 3){
				throw new RestException(30004);
			}else
				throw new RestException(-1);
		}catch(SQLException e){
			e.printStackTrace();
			throw new RestException(e);
		}
		return svcre;
	}

	
	
	/**
	 * 通过流程ID获取流程实例
	 */
	@Override
	public ServiceExcuteResult getAlphaflowInstanceByWfId(Integer wfId) throws Exception {
		ServiceExcuteResult res = new ServiceExcuteResult();
		try{
			AlphaflowInstanceBean aibean =  getAlphaflowInstanceBeanByWfId(wfId);
			res.setResult(aibean);
		}catch(Exception e){
			e.printStackTrace();
			throw new RestException(e);
		}
		return res;
	}
	
	 @Override
	public AlphaflowInstanceBean getAlphaflowInstanceBeanByWfId(Integer wfId) throws Exception {
		 AlphaflowInstanceBean aibean =  alphaflowInstanceMapper.getAlphaflowInstanceByWfId(wfId);
		aibean.setStatus(AlphaflowUtil.AlphaflowInstanceStatus.getStatusName(aibean.getStatus_id()));
		List<Integer> itask = alphaflowTaskMapper.getCurrentTaskIdsByWfId(aibean.getWfId());
		aibean.setCurrent_taskid(itask);
		return aibean;
	}
	

	@Override
	public ServiceExcuteResult testAlphaflow() throws Exception {
		ServiceExcuteResult res = new ServiceExcuteResult();
		JSONObject js = new JSONObject();
		js.put("aa", "1");
		res.setResult(js);
		return res;
	}

	@Override
	public ServiceExcuteResult updateCancelAlphaflowByWfId(Integer wfId, String userId) throws Exception {
			AlphaflowInstanceBean afbean = alphaflowInstanceMapper.getAlphaflowInstanceByWfId(wfId);
			if(afbean==null){
				throw new RestException(30002);
			}	
			ServiceExcuteResult svcre = new ServiceExcuteResult();
			HashMap<String ,Object> params = new HashMap<String,Object>();
			params.put("wfId", wfId);
			params.put("userId", userId);
			try{
				alphaflowInstanceMapper.cancelAlphaflowByWfId(params);
				svcre.setResult("终止操作成功");
			}catch(SQLException e){
				e.printStackTrace();
				throw new RestException(e);
			}
			return svcre;
	}

	@Override
	public ServiceExcuteResult getAlphaflowInstanceList(JSONObject searchParams) throws Exception {
		ServiceExcuteResult svcre = new ServiceExcuteResult();
		//name   category  sub_category   inituser    createdate_s    createdate_e
		
		try{
			int pageNum = PaginationUtil.getPageNum(searchParams);
			int pageSize = PaginationUtil.getPageSize(searchParams);
			
			
			SearchListExample sExp = new SearchListExample();
			SearchListExample.Criteria c = sExp.createCriteria();
			
			String sort = SearchUtil.getSearchVal("sort", searchParams);
			String order = StringUtil.isBlank(SearchUtil.getSearchVal("order", searchParams),"asc");		
			if(!StringUtil.isBlank(sort)){
				try{
					JSONArray sortarr = JSONArray.fromObject(sort);
					String orderby = PaginationUtil.getOrderBy(sortarr, order);
					sExp.setOrderByClause(orderby);
				}catch(Exception e){e.printStackTrace();}
			}		
			
			String statusval = SearchUtil.getSearchVal("status", searchParams);
			if(!StringUtil.isBlank(statusval)){
				int statusid = AlphaflowUtil.AlphaflowInstanceStatus.getStatusId(statusval);
				if(statusid!=-100){
					c.addCriterion("status_id =", statusid, "status_id");
				}
			}
			String nameval = SearchUtil.getSearchVal("name", searchParams);
			if(!StringUtil.isBlank(nameval)){
				c.andColLike("request_desc", nameval);
			}
			String categoryval = SearchUtil.getSearchVal("category", searchParams);
			if(!StringUtil.isBlank(categoryval)){
				Integer catid = alphaflowTemplateMapper.getAlphaflowGroupIdByName(categoryval);
				if(catid!=null&&catid!=0){
					c.addCriterion("group_id =", catid, "group_id");
				}
			}
			String sub_categoryval = SearchUtil.getSearchVal("sub_category", searchParams);
			if(!StringUtil.isBlank(sub_categoryval)){
				Integer catid = alphaflowTemplateMapper.getAlphaflowSubGroupIdByName(sub_categoryval);
				if(catid!=null&&catid!=0){
					c.addCriterion("sub_group_id =", catid, "sub_group_id");
				}
			}
			String inituser = SearchUtil.getSearchVal("init_id", searchParams);
			if(!StringUtil.isBlank(inituser)){
				UserBean ubean= oaUserMapper.getUserBeanByUserId(inituser);
				if(ubean!=null){
					c.addCriterion("init_id = ",ubean.getMyWsId(),"init_id");
				}
			}
			
			String createdate_s = SearchUtil.getSearchVal("create_date_s", searchParams);
			String createdate_e = SearchUtil.getSearchVal("create_date_e", searchParams);
			if(!StringUtil.isBlank(createdate_s)){
				c.addCriterion("create_date >= ","to_date('"+createdate_s+"','yyyy-mm-dd')","create_date");			
			}
			if(!StringUtil.isBlank(createdate_e)){
				c.addCriterion("create_date <= ","to_date('"+createdate_e+"','yyyy-mm-dd')","create_date");			
			}
			PageHelper.startPage(pageNum, pageSize);
			List<AlphaflowInstanceBean> aibeans =  alphaflowInstanceMapper.getAlphaflowInstanceList(sExp);		
			PageInfo<AlphaflowInstanceBean> pageInfo = new PageInfo<AlphaflowInstanceBean>(aibeans);
			for(int i=0;i<aibeans.size();i++){
				AlphaflowInstanceBean aibean = aibeans.get(i);
				aibean.setStatus(AlphaflowUtil.AlphaflowInstanceStatus.getStatusName(aibean.getStatus_id()));
				List<Integer> itask = alphaflowTaskMapper.getCurrentTaskIdsByWfId(aibean.getWfId());
				aibean.setCurrent_taskid(itask);
			}
			JSONObject reobj = new JSONObject();
			
			reobj.put("total", pageInfo.getTotal());
			reobj.put("start", pageNum);
			reobj.put("size",  pageSize);
			if(!StringUtil.isBlank(sort)){
				reobj.put("sort", sort);
				reobj.put("order", order);
			}
			reobj.put("data", JsonUtils.objectToJson(aibeans));		
			svcre.setResult(reobj);
		}catch(Exception e){
			e.printStackTrace();
			throw new RestException(e);
		}
	
		return svcre;
		
	}

	@Override
	public ServiceExcuteResult getAlphaflowInstanceTaskList(Integer wfId) throws Exception {
		ServiceExcuteResult serviceExcuteResult = new ServiceExcuteResult();
		try{
			List<AlphaflowTaskBean> atbean = alphaflowInstanceMapper.getAlphaflowInstanceTaskList(wfId);		
			if(atbean!=null){
				atbean = AlphaflowUtil.filterDistinctTaskList(atbean);
				String re = AlphaflowJackson.json().filterIn("alphaflowTaskFilter", new String[]{"wfId","task_id","task_name"
				                                                                    ,"status", "task_level","assignee","assignee_name"})				
						.readAsString(atbean);
				serviceExcuteResult.setResult(re);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RestException(e);
		}
		return serviceExcuteResult;
	}
	
	@Override
	public ServiceExcuteResult jumpAlphaflowInstanceTask(Integer wfId, Integer taskFrom, Integer taskTo)
			throws Exception {
		ServiceExcuteResult serviceExcuteResult = new ServiceExcuteResult();
		AlphaflowInstanceBean aibean = alphaflowInstanceMapper.getAlphaflowInstanceByWfId(wfId);
		if(aibean.getStatus_id() == AlphaflowUtil.AlphaflowInstanceStatus.CANCELLED_STATUS
				||aibean.getStatus_id() == AlphaflowUtil.AlphaflowInstanceStatus.COMPLETED_STATUS)
			throw new RestException(30005);				
		try{
			Map<String ,Object> params = new HashMap<String ,Object>();
			params.put("wfId", wfId);params.put("taskFrom", taskFrom);params.put("taskTo", taskTo);
			params.put("userId", "newsoft");
			alphaflowInstanceMapper.jumpAlphaflowTask(params);
		}catch(Exception e){
			e.printStackTrace();
			throw new RestException(e);
		}		
		serviceExcuteResult.setResult("跳转成功");
		return serviceExcuteResult;
	}
	
	
	
}
