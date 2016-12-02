package org.ccps.alphaflow.api.controller;

import org.ccps.alphaflow.api.common.ExceptionUtil;
import org.ccps.alphaflow.api.common.RestResult;
import org.ccps.alphaflow.api.common.ServiceExcuteResult;
import org.ccps.alphaflow.api.service.AlphaflowTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import net.sf.json.JSONObject;

@RestController
@RequestMapping(value = "/alphaflow/template")
public class AlphaflowTemplateController  {
	@Autowired
	private AlphaflowTemplateService alphaflowTemplateService;
	
	/**
	 * 流程模板列表 搜索
	 * @param initParam
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET )
	public RestResult getAplaflowTemplateList(@RequestBody JSONObject initParam){		
		try {
			ServiceExcuteResult serviceExcuteResult = alphaflowTemplateService.getAlphaflowTemplateList(initParam);
			return new RestResult(0, serviceExcuteResult.getParamreStr() ,serviceExcuteResult.getResult());
		}catch (Exception e) {return ExceptionUtil.exceptionWapper(e);}
	
	}
}
