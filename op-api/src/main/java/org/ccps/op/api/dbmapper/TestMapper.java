package org.ccps.op.api.dbmapper;

import org.apache.ibatis.annotations.Results;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.ccps.common.db.*;

public interface TestMapper extends WhDbMapper {
	
	 @Select("SELECT * FROM user_table WHERE user_id = #{userid}")
	 @Results(value = { @Result(id = true, column = "mi", property = "name"),
	 @Result(column = "user_id", property = "userId") })
	 public  List<Map<String,Object>> getUsers(@Param("userid") String userid);
}
