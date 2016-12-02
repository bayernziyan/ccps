package org.ccps.alphaflow.api.dbmapper.jiulong;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.ccps.common.db.WhDbMapper;

import net.sf.json.JSONObject;

public interface AlphaflowTaskMapperJIULONG extends WhDbMapper {	

	/**
	 * 执行wtta
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Integer alphaflowTaskForward(Map params) throws SQLException;

}
