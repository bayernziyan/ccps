package org.ccps.alphaflow.api.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ccps.alphaflow.api.common.ExceptionUtil;
import org.ccps.alphaflow.api.common.JsonUtils;
import org.ccps.alphaflow.api.common.RestException;
import org.ccps.alphaflow.api.common.RestResult;
import org.ccps.alphaflow.api.common.SearchUtil;
import org.ccps.alphaflow.api.common.ServiceExcuteResult;
import org.ccps.alphaflow.api.pojo.AlphaflowInitParam;
import org.ccps.alphaflow.api.pojo.AlphaflowInstanceBean;
import org.ccps.alphaflow.api.pojo.AlphaflowItemParam;
import org.ccps.alphaflow.api.service.AlphaflowFormService;
import org.ccps.alphaflow.api.service.AlphaflowInstanceService;
import org.ccps.alphaflow.api.service.AlphaflowTaskService;
import org.ccps.common.util.StringUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.TraceRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 *  流程接口视图
 * 
 */
@RestController
@RequestMapping("/alphaflow/instance")
public class AlphaflowInstanceController {
	private static final Logger logger = LoggerFactory.getLogger(AlphaflowInstanceController.class);
	@Autowired
	private AlphaflowInstanceService alphaflowInstanceService;
	@Autowired
	private AlphaflowTaskService alphaflowTaskService;
	@Autowired
	private AlphaflowFormService alphaflowFormService;
	@Autowired
	private TraceRepository traceRepository;
	
	/**
	 * 启动流程
	 * @param wfdef
	 * @param initParam
	 * @param headers
	 * @return
	 */
	@RequestMapping(value = "/{wfdef}/create",method = RequestMethod.POST)
	public RestResult initAlphaflow(@PathVariable String wfdef,@RequestBody JSONObject initParam){	
		/*Map<String,Object> trace = new HashMap<String,Object>();
		JSONObject _p_in = new JSONObject();
		_p_in.put("wfdef", wfdef);
		_p_in.put("initParam", initParam);
		trace.put("requestBody", _p_in);*/
		AlphaflowInitParam initobj = JsonUtils.jsonToPojo(initParam.toString(), AlphaflowInitParam.class);
		initobj.setWfdef(wfdef);
		
		String wfcode = null;
		String currtasks = "";
		String remsg = "";
		try {
			ServiceExcuteResult serviceExcuteResult = alphaflowInstanceService.initAlphaflow(initobj);
			Object reobj = serviceExcuteResult.getResult();
			if(reobj!=null){
				JSONObject rejson = (JSONObject)reobj;				
				wfcode = rejson.getString("wfId");
				currtasks = rejson.getString("currTasks");
			}
			JSONArray paramre = serviceExcuteResult.getParamre();
			if(paramre!=null) remsg = paramre.toString();
		} catch (Exception e) { RestResult er = ExceptionUtil.exceptionWapper(e); /*trace.put("responseBody", er); traceRepository.add(trace);*/  return er;} 
		
		JSONObject reobj = new JSONObject();
		reobj.put("wfcode", wfcode);
		reobj.put("currtasks", currtasks);
		//System.out.println(JsonUtils.objectToJson(initobj));
		RestResult ir = null;
		if(!StringUtil.isBlank(wfcode))
			ir = new RestResult(0,remsg,reobj);
		else
			ir = new RestResult(30001,remsg,null);
	/*	trace.put("responseBody", ir);
		
		traceRepository.add(trace);*/
		return ir;
	}
	/**
	 * 上传流程附件
	 * @param wfId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{wfId}/uploadfiles",method = RequestMethod.POST)
	public RestResult uploadAlphaflowFiles(@PathVariable String wfId,HttpServletRequest request,HttpServletResponse response) {
		try {
			ServiceExcuteResult serviceExcuteResult = alphaflowInstanceService.uploadAlphaflowFiles(request,Integer.parseInt(wfId));			
			return new RestResult(0, serviceExcuteResult.getParamreStr() ,serviceExcuteResult.getResult());
		}catch (Exception e) {
			return ExceptionUtil.exceptionWapper(e);
		} 
		//return new RestResult(0,"",null);
	}
	/**
	 * 撤销流程
	 * @param wfId
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/{wfId}/revoke",method = RequestMethod.PUT)
	public RestResult revokeAlphaflow(@PathVariable Integer wfId,@RequestParam(value="userId",required=false) String userId){
		try {
			ServiceExcuteResult serviceExcuteResult = alphaflowInstanceService.updateRevokeAlphaflowByWfId(wfId, userId);
			return new RestResult(0, serviceExcuteResult.getParamreStr() ,serviceExcuteResult.getResult());
		}catch (Exception e) {return ExceptionUtil.exceptionWapper(e);} 	
	}
	
	@RequestMapping(value = "/{wfId}/cancel",method = RequestMethod.PUT)
	public RestResult cancelAlphaflow(@PathVariable Integer wfId,@RequestParam(value="userId",required=false) String userId){
		try {
			ServiceExcuteResult serviceExcuteResult = alphaflowInstanceService.updateCancelAlphaflowByWfId(wfId, userId);
			return new RestResult(0, serviceExcuteResult.getParamreStr() ,serviceExcuteResult.getResult());
		}catch (Exception e) {return ExceptionUtil.exceptionWapper(e);} 	
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public RestResult getAlphaflowInstanceList(@RequestBody JSONObject initParam){
		try {
			ServiceExcuteResult serviceExcuteResult = alphaflowInstanceService.getAlphaflowInstanceList(initParam);
			return new RestResult(0, serviceExcuteResult.getParamreStr() ,serviceExcuteResult.getResult());
		}catch (Exception e) {return ExceptionUtil.exceptionWapper(e);} 	
	}
	@RequestMapping(value = "/{wfId}/info", method = RequestMethod.GET)
	public RestResult getAlphaflowInstance(@PathVariable Integer wfId){
		try {
			ServiceExcuteResult serviceExcuteResult = alphaflowInstanceService.getAlphaflowInstanceByWfId(wfId);
			return new RestResult(0, serviceExcuteResult.getParamreStr() ,serviceExcuteResult.getResult());
		}catch (Exception e) {return ExceptionUtil.exceptionWapper(e);} 	
	}
	
	@RequestMapping(value = "/{wfId}/tasks", method = RequestMethod.GET)
	public RestResult getAlphaflowInstanceTaskList(@PathVariable Integer wfId){
		try {
			ServiceExcuteResult serviceExcuteResult = alphaflowInstanceService.getAlphaflowInstanceTaskList(wfId);
			return new RestResult(0, serviceExcuteResult.getParamreStr() ,serviceExcuteResult.getResult());
		}catch (Exception e) {return ExceptionUtil.exceptionWapper(e);} 	
	}
	
	@RequestMapping(value = "/{wfId}/jump", method = RequestMethod.POST)
	public RestResult jumpAlphaflowInstanceTask(@PathVariable Integer wfId,@RequestParam(value="taskId_from") Integer tid_f,@RequestParam(value="taskId_to") Integer tid_t){
		try {
			ServiceExcuteResult serviceExcuteResult = alphaflowInstanceService.jumpAlphaflowInstanceTask(wfId,tid_f,tid_t);
			return new RestResult(0, serviceExcuteResult.getParamreStr() ,serviceExcuteResult.getResult());
		}catch (Exception e) {return ExceptionUtil.exceptionWapper(e);} 	
	}
	
	@RequestMapping(value = "/{wfId}/form/data",method = RequestMethod.POST)
	public RestResult alphaflowInstanceFormData(@PathVariable Integer wfId,@RequestBody JSONObject params){
		ServiceExcuteResult serviceExcuteResult = new ServiceExcuteResult();
		try {
			String itemdefs = SearchUtil.getSearchVal("itemdefs", params);
			if(!StringUtil.isBlank(itemdefs)){
				JSONArray itemarr = JSONArray.fromObject(itemdefs);
				serviceExcuteResult = alphaflowFormService.getAlphaflowInstanceFormDataByItemDef(wfId, itemarr);
			}			
			return new RestResult(0, serviceExcuteResult.getParamreStr() ,serviceExcuteResult.getResult());
		}catch (Exception e) {return ExceptionUtil.exceptionWapper(e);} 
	}
	/**
	 * 流程表单负责
	 * @param wfId
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/{wfId}/form/setdata",method = RequestMethod.POST)
	public RestResult alphaflowInstanceSetFormData(@PathVariable Integer wfId ,@RequestBody JSONObject jsonparams){
		ServiceExcuteResult serviceExcuteResult = new ServiceExcuteResult();
		try {
			AlphaflowInstanceBean afbean = alphaflowInstanceService.getAlphaflowInstanceBeanByWfId(wfId);
			if(afbean==null){
				throw new RestException(30002);
			}
			
			JSONArray params = jsonparams.containsKey("params")?jsonparams.getJSONArray("params"):null;
			if(params==null)throw new RestException(100);
			List<AlphaflowItemParam>  aiparams = JsonUtils.jsonToList(params.toString(), AlphaflowItemParam.class);
			serviceExcuteResult = alphaflowFormService.updateAlphaflowInstanceSetFormData(afbean, aiparams);
			return new RestResult(0, serviceExcuteResult.getParamreStr() ,serviceExcuteResult.getResult());
		}catch (Exception e) {return ExceptionUtil.exceptionWapper(e);} 
	}
	
	
	@RequestMapping(value = "/test",method = RequestMethod.PUT)
	public RestResult testAlphaflow(){
		/*ApplicationContext factory = new FileSystemXmlApplicationContext("D:/msproject/econ-apis-rest/econ-apis-rest-web/src/main/resources/spring/*.xml"); 
		String projectcode = "ata";
		String mappername = "com.apis.mapper.AlphaflowTaskMapper";
		String  mappername1 = mappername.substring(0,16)+projectcode+mappername.substring(17)+projectcode.toUpperCase();
		System.out.println(mappername1);
		Object  a  = null;
	
		
		try {
			Class c1 = Class.forName(mappername1);
			sqlSession.getMapper(c1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		if(factory.containsBean(mappername1))
			a =  factory.getBean(mappername1);
		else
			a = factory.getBean(mappername);
		System.out.println("a1:"+(a==null));*/
		
		try {
			ServiceExcuteResult serviceExcuteResult = 	alphaflowTaskService.updateAlphaflowTaskForward(12312, null, "bill");
			return new RestResult(0, serviceExcuteResult.getParamreStr() ,serviceExcuteResult.getResult());
		} catch (Exception e) {return ExceptionUtil.exceptionWapper(e);} 
		
	}
	
}
