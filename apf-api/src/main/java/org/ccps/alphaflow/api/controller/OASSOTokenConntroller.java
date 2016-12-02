package org.ccps.alphaflow.api.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.ccps.alphaflow.api.common.ExceptionUtil;
import org.ccps.alphaflow.api.common.RequestUtil;
import org.ccps.alphaflow.api.common.RestResult;
import org.ccps.alphaflow.api.common.ServiceExcuteResult;
import org.ccps.alphaflow.api.service.OASSOTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import net.sf.json.JSONObject;

/**
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/security/ssotoken")
public class OASSOTokenConntroller {
	
	@Autowired
	private OASSOTokenService oASSOTokenService;
	
	@RequestMapping(value = "/create",method = RequestMethod.POST)
	public RestResult createSSOToken(@RequestParam(value="userId") String userId,HttpServletRequest request){	
		String ip = "";
		try{
			ip = RequestUtil.getIpAddress(request);
			ServiceExcuteResult serviceExcuteResult = oASSOTokenService.createToken(userId, ip);
			return RestResult.build(0, "", serviceExcuteResult.getResult());
		}catch(Exception e){
			return ExceptionUtil.exceptionWapper(e);
		}
	}
}
