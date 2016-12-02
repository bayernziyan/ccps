package org.ccps.alphaflow.api.dbmapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Param;
import org.ccps.common.db.WhDbMapper;

public interface KeyValueMapper extends WhDbMapper {
	public Integer getKeyValueIdByCodeFromSys(@Param("catId") Integer catId,@Param("code") String code) throws SQLException;
	
	public Integer getKeyValueIdByCodeFromWf(@Param("itemId") Integer itemId,@Param("code") String code) throws SQLException;
	
	public String getKeyValueDescByIdFromSys(@Param("id") Integer id) throws SQLException;
	
	public String getKeyValueDescByIdFromWf(@Param("id") Integer id) throws SQLException;
}
