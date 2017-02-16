package org.ccps.organization.api.dbmapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.ccps.common.db.repository.WhDbRepository;
import org.ccps.organization.api.pojo.Department;

@WhDbRepository
public interface DeptMapper {
//@Param("i_keycolumn") String keycolumn,@Param("i_dept") Department dept,@Param("o_result") String result
	public void addDept(Map<String,Object> map) throws SQLException;
	
	public void updateDept(Map<String,Object> map) throws SQLException;
	
	public void deleteDept(Map<String,Object> map) throws SQLException;

	public Department selectDeptDetailByCode(@Param("i_keycolumn") String keycolumn,@Param("i_deptcode") String deptCode) throws SQLException;
	
	public List<Department> selectValidDeptDetailListByRoot(@Param("i_keycolumn") String keycolumn,@Param("i_rootcode") String rootCode) throws SQLException;
}
