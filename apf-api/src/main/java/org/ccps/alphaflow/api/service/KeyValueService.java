package org.ccps.alphaflow.api.service;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Param;

public interface KeyValueService {
	
	/**
	 * 获取系统基础数据ID
	 * @param catId
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public Integer getKeyValueIdFromSys(Integer catId,String code) throws Exception;
	/**
	 * 获取流程表单自填基础数据ID
	 * @param itemId
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public Integer getKeyValueIdFromWf(Integer itemId,String code) throws Exception;
	/**
	 * 获取系统基础数据值
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String getKeyValueDescByIdFromSys(Integer id) throws Exception;
	/**
	 * 获取流程基础数据值
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String getKeyValueDescByIdFromWf(Integer id) throws Exception;
	
}
