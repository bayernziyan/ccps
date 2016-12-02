package org.ccps.alphaflow.api.service;

import org.ccps.alphaflow.api.common.ServiceExcuteResult;

public interface OASSOTokenService {
	public ServiceExcuteResult createToken(String userId,String ip) throws Exception;
}
