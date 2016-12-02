package org.ccps.alphaflow.api.service.impl;
import java.util.Calendar;

import org.ccps.alphaflow.api.common.Eryptogram;
import org.ccps.alphaflow.api.common.RestException;
import org.ccps.alphaflow.api.common.ServiceExcuteResult;
import org.ccps.alphaflow.api.service.OASSOTokenService;
import org.ccps.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class OASSOTokenServiceImpl implements OASSOTokenService {
	@Value("${oa.systemkey}")
	private String systemkey;
		
	@Override
	public ServiceExcuteResult createToken(String userId, String ip) throws Exception  {
		if(StringUtil.isBlank(userId)) throw new RestException(101);
		if(StringUtil.isBlank(systemkey))	throw new RestException(102);  	
	
		Calendar cal = Calendar.getInstance();
	  	String time = String.valueOf(cal.getTimeInMillis());
	    String key = userId+"|"+time+"|"+ip;
		String    encryptkey = Eryptogram.encryptOp(key, systemkey);
		
		ServiceExcuteResult serviceExcuteResult = new ServiceExcuteResult();
		serviceExcuteResult.setResult(encryptkey);
		return serviceExcuteResult;
	}

	
	
}
