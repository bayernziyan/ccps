package org.ccps.alphaflow.api.service.impl;

import org.ccps.alphaflow.api.dbmapper.KeyValueMapper;
import org.ccps.alphaflow.api.service.KeyValueService;
import org.ccps.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 基础数据
 * @author Administrator
 *
 */
@Service
public class KeyValueServiceImpl implements KeyValueService {
	@Autowired
	private KeyValueMapper keyvalueMapper;
	@Override
	public Integer getKeyValueIdFromSys(Integer catId, String code) throws Exception {
		// TODO Auto-generated method stub
		if(StringUtil.isBlank(code)||catId == 0)return 0;
		return keyvalueMapper.getKeyValueIdByCodeFromSys(catId, code);
	}

	@Override
	public Integer getKeyValueIdFromWf(Integer itemId, String code) throws Exception {
		// TODO Auto-generated method stub
		if(StringUtil.isBlank(code)||itemId == 0)return 0;
		return keyvalueMapper.getKeyValueIdByCodeFromWf(itemId, code);
	}

	@Override
	public String getKeyValueDescByIdFromSys(Integer id) throws Exception {
		return keyvalueMapper.getKeyValueDescByIdFromSys(id);
	}
	
	@Override
	public String getKeyValueDescByIdFromWf(Integer id) throws Exception {
		return keyvalueMapper.getKeyValueDescByIdFromWf(id);
	}
}
