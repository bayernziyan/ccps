package org.ccps.alphaflow.api.service.ata;

import javax.servlet.http.HttpServletRequest;

import org.ccps.alphaflow.api.common.ServiceExcuteResult;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import net.sf.json.JSONArray;

public interface AlphaflowTaskServiceATA {

	/**
	 * 流程流转到下一环节 调用wtta
	 * 
	 * @param taskId
	 * @param assigners
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public ServiceExcuteResult updateAlphaflowTaskForward(Integer taskId,JSONArray assigners,String userId) throws Exception;
}
