package org.ccps.alphaflow.api.service;


import org.ccps.alphaflow.api.common.ServiceExcuteResult;

import net.sf.json.JSONObject;

public interface AlphaflowTemplateService {
	
	/**
	 * 通过参数查询 流程模板
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public ServiceExcuteResult getAlphaflowTemplateList(JSONObject params) throws Exception;
}
