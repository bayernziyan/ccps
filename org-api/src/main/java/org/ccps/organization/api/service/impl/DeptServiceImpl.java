package org.ccps.organization.api.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ccps.common.util.StringUtil;
import org.ccps.organization.api.common.ServiceResult;
import org.ccps.organization.api.dbmapper.DeptMapper;
import org.ccps.organization.api.pojo.Department;
import org.ccps.organization.api.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DeptServiceImpl implements DeptService {

	/*
	 * oa.sync.dept_keycolumn=dept_id
oa.sync.user_keycolumn=user_id
oa.sync_dept_type=2
	 */
	@Value("${oa.sync.dept_keycolumn}")
	private String keycolumn;
	@Value("${oa.sync_dept_type}")
	private int depttype;
	
	@Autowired
	private DeptMapper deptMapper;
	
	/**
	 * 批量操作
	 * 1增 2删 3改
	 */
	public List<ServiceResult> deptBatchCommonDML(List<Department> dlist,int flag){
		List<ServiceResult> relist = new ArrayList<ServiceResult>();
		for(Department dept : dlist){
			if( null  == dept)continue;
			relist.add(deptCommonDML(dept, flag));
		}
		return relist;
	}
	
	
	/**
	 * 1增 2删 3改
	 */
	@Override
	public ServiceResult deptCommonDML(Department dept, int flag) {
		ServiceResult result = new ServiceResult();
		String re = "";
		try{
			dept.setDeptType(depttype);
			Map<String ,Object> map = new HashMap<String,Object>();
			map.put("i_keycolumn", keycolumn);
			map.put("i_dept", dept);map.put("o_result", new String());
			//check
			if(StringUtil.isBlank(dept.getDeptCode())){
				result.setCode("error");
				result.setMsg("缺少编码" );
				result.setData(dept);
				return result;
			}
			Department old_d = getValidDeptDetailByCode(dept.getDeptCode());
			if(null != old_d && flag == 1){
				result.setCode("error");
				result.setMsg("部门编码已经存在" );
				result.setData(old_d);
				return result;
			}else if( null== old_d && (flag==2 || flag==3)){
				result.setCode("error");
				result.setMsg("部门编码不存在" );
				result.setData(dept);
				return result;
			}
			
			if( flag == 1 ){
				//add
				deptMapper.addDept(map);
			} else if( flag == 2 ) {
				//update
				deptMapper.updateDept(map);
			} else if( flag == 3 ) {
				//delete
				deptMapper.deleteDept(map);
			}
			re = String.valueOf( map.get("o_result"));
		} catch(Exception e){
			e.printStackTrace();
			result.setCode("error");
			result.setMsg("[" + dept.getDeptCode()+ "] "+e.getMessage() );
			return result;
		}
		if(!StringUtil.isBlank(re)&& "1".equals(String.valueOf(re.charAt(0)))){
			result.setCode("success");
			result.setMsg("[" + dept.getDeptCode()+ "]" +re.substring(2));
		}else
		{
			result.setCode("error");
			result.setMsg("[" + dept.getDeptCode()+ "]" + (StringUtil.isBlank(re)?"出错啦":re.substring(2)));
		}
		return result;
	}


	Department getValidDeptDetailByCode(String deptCode){
		try{
			Department dept = deptMapper.selectDeptDetailByCode(keycolumn, deptCode);
			if(dept!=null&&dept.getStatusCode() != 1)
				return null;
			return dept;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public ServiceResult selectDeptDetailByCode(String deptCode) {
		ServiceResult result = new ServiceResult();
		String re = "";
		try{
			Department dept = deptMapper.selectDeptDetailByCode(keycolumn, deptCode);
			if( null == dept ){
				result.setCode("error");
				result.setMsg("[" + deptCode+ "] 部门不存在" );
				return result; 
			}
			result.setCode("success");
			result.setData(dept);
		} catch(Exception e){
			e.printStackTrace();
			result.setCode("error");
			result.setMsg("[" + deptCode + "] "+e.getMessage() );
			return result;
		}
		return result;
	}


	@Override
	public ServiceResult selectValidDeptDetailListByRoot(String rootCode) {
		ServiceResult result = new ServiceResult();
		try {
			List<Department> dlist = deptMapper.selectValidDeptDetailListByRoot(keycolumn, rootCode) ;
			result.setCode("success");
			result.setData(dlist);
		} catch (SQLException e) {			
			e.printStackTrace();
			result.setCode("error");
			result.setMsg("[" + rootCode + "] "+e.getMessage() );
			return result;
		}
		return result;
	}
	
	
}
