package org.ccps.alphaflow.api.dbmapper.ata;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.ccps.common.db.WhDbMapper;
import org.ccps.common.db.OpDbMapper;

import net.sf.json.JSONObject;

public interface AlphaflowFormMapperATA extends WhDbMapper {	

	
	public void setNormalItemValueOriginal(@Param("wfId") Integer wfId,@Param("formDataId") Integer formDataId,@Param("itemId") Integer itemId,@Param("value") String value,@Param("taskId") Integer taskId,@Param("userId") String userId,@Param("multiAssigneeStr") String multiAssigneeStr) throws SQLException;
	
	
}
