package org.ccps.alphaflow.api.dbmapper.ata;

import java.sql.SQLException;
import java.util.Map;

import org.ccps.common.db.repository.WhDbRepository;

@WhDbRepository
public interface AlphaflowTaskMapperATA  {	

	/**
	 * 执行wtta
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Integer alphaflowTaskForward(Map params) throws SQLException;

}
