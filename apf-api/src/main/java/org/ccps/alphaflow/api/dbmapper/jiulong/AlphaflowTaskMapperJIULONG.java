package org.ccps.alphaflow.api.dbmapper.jiulong;

import java.sql.SQLException;
import java.util.Map;

import org.ccps.common.db.repository.WhDbRepository;

@WhDbRepository
public interface AlphaflowTaskMapperJIULONG {	

	/**
	 * 执行wtta
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Integer alphaflowTaskForward(Map params) throws SQLException;

}
