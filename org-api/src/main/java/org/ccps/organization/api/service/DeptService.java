package org.ccps.organization.api.service;

import java.util.List;

import org.ccps.organization.api.common.ServiceResult;
import org.ccps.organization.api.pojo.Department;

public interface DeptService {

	public ServiceResult deptCommonDML(Department dept, int flag) ;
	
	public List<ServiceResult> deptBatchCommonDML(List<Department> dlist,int flag);
	
	public ServiceResult selectDeptDetailByCode(String deptCode);
	
	public ServiceResult selectValidDeptDetailListByRoot(String rootCode);
}
