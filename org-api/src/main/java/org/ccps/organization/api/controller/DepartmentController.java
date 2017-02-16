package org.ccps.organization.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.ccps.common.db.MultipleDataSourceAspectAdvice;
import org.ccps.organization.api.common.ServiceResult;
import org.ccps.organization.api.pojo.Department;
import org.ccps.organization.api.service.DeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/dept")
public class DepartmentController {
	private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
	@Autowired
	private DeptService deptService;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST )
	public HttpEntity<ServiceResult> addOne(@RequestBody Department dept){
		return new HttpEntity<>(deptService.deptCommonDML(dept, 1));
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST )
	public HttpEntity<ServiceResult> updateOne(@RequestBody Department dept){
		return new HttpEntity<>(deptService.deptCommonDML(dept, 2));
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST )
	public HttpEntity<ServiceResult> deleteOne(@RequestParam(value="deptCode",required=true) String deptCode){
		Department dept = new Department();
		dept.setDeptCode(deptCode);dept.setStatusCode(2);
		return new HttpEntity<>(deptService.deptCommonDML(dept, 3));
	}
	
	
	@RequestMapping(value = "/multi/add", method = RequestMethod.POST )
	public HttpEntity<List<ServiceResult>> addMulti(@RequestBody List<Department> depts){
		return new HttpEntity<>(deptService.deptBatchCommonDML(depts, 1));
	}
	
	@RequestMapping(value = "/multi/update", method = RequestMethod.POST )
	public HttpEntity<List<ServiceResult>> updateMulti(@RequestBody List<Department> depts){
		return new HttpEntity<>(deptService.deptBatchCommonDML(depts, 2));
	}
	
	@RequestMapping(value = "/multi/delete", method = RequestMethod.POST )
	public HttpEntity<List<ServiceResult>> deleteMulti(@RequestBody List<String> deptCodes){
		List<Department> depts = new ArrayList<Department>();
		for(String code : deptCodes){
			Department dept = new Department();
			dept.setDeptCode(code);dept.setStatusCode(2);
			depts.add(dept);
		}
		return new HttpEntity<>(deptService.deptBatchCommonDML(depts, 3));
	}
	
	@RequestMapping(value = "/detail", method =  {RequestMethod.GET, RequestMethod.POST} )
	public HttpEntity<ServiceResult> selectOne(@RequestParam(value="deptCode",required=true) String deptCode){
		return new HttpEntity<>(deptService.selectDeptDetailByCode(deptCode));
	}
	
	@RequestMapping(value = "/list/root", method =  {RequestMethod.GET, RequestMethod.POST} )
	public HttpEntity<ServiceResult> selectListByRoot(@RequestParam(value="rootCode",required=false,defaultValue="0") String rootCode){
		return new HttpEntity<>(deptService.selectValidDeptDetailListByRoot(rootCode));
	}
	
}
