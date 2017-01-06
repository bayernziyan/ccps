package org.ccps.alphaflow.api.dbmapper.ata;

import java.sql.SQLException;
import org.apache.ibatis.annotations.Param;
import org.ccps.common.db.repository.WhDbRepository;


@WhDbRepository
public interface AlphaflowFormMapperATA {	

	
	public void setNormalItemValueOriginal(@Param("wfId") Integer wfId,@Param("formDataId") Integer formDataId,@Param("itemId") Integer itemId,@Param("value") String value,@Param("taskId") Integer taskId,@Param("userId") String userId,@Param("multiAssigneeStr") String multiAssigneeStr) throws SQLException;
	
	
}
