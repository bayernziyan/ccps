package org.ccps.organization.api.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ccps.common.db.util.PageUtil;
import org.ccps.common.db.util.SearchListExample;
import org.ccps.common.db.util.SearchWapper;
import org.ccps.common.util.JSONUtil;
import org.ccps.common.util.StringUtil;
import org.ccps.organization.api.common.ServiceResult;
import org.ccps.organization.api.dbmapper.UserMapper;
import org.ccps.organization.api.pojo.Department;
import org.ccps.organization.api.pojo.User;
import org.ccps.organization.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.PagerUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;

@Service
public class UserServiceImpl implements UserService {
	/*
	 * oa.sync.dept_keycolumn=dept_id
oa.sync.user_keycolumn=user_id
oa.sync_dept_type=2
	 */
	@Value("${oa.sync.user_keycolumn}")
	private String ukeycolumn;
	@Value("${oa.sync.dept_keycolumn}")
	private String dkeycolumn;
	@Value("${oa.sync_dept_type}")
	private int depttype;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public ServiceResult userCommonDML(User user, int flag) {
		ServiceResult result = new ServiceResult();
		String re = "";
		try{
			//@Param("i_dkeycolumn") String dkeycolumn,@Param("i_ukeycolumn") String ukeycolumn,@Param("i_user") User user,@Param("o_result") String result
			user.setDeptType(depttype);
			Map<String ,Object> map = new HashMap<String,Object>();
			map.put("i_dkeycolumn", dkeycolumn);map.put("i_ukeycolumn", ukeycolumn);
			map.put("i_user", user);map.put("o_result", new String());
			//check
			if(StringUtil.isBlank(user.getUserCode())){
				result.setCode("error");
				result.setMsg("缺少编码" );
				result.setData(user);
				return result;
			}
			User old_u = getValidUserDetailByCode( user.getUserCode());
			if(null != old_u && flag == 1){
				result.setCode("error");
				result.setMsg("用户编码已经存在" );
				result.setData(old_u);
				return result;
			}else if( null== old_u && (flag==2 || flag==3)){
				result.setCode("error");
				result.setMsg("用户编码不存在" );
				result.setData(user);
				return result;
			}
			
			if( flag == 1 ){
				//add
				userMapper.addUser(map);
			} else if( flag == 2 ) {
				//update
				userMapper.updateUser(map);
			} else if( flag == 3 ) {
				//delete
				userMapper.deleteUser(map);
			}
			re = String.valueOf( map.get("o_result"));
		} catch(Exception e){
			e.printStackTrace();
			result.setCode("error");
			result.setMsg("[" + user.getUserCode()+ "] "+e.getMessage() );
			return result;
		}
		if(!StringUtil.isBlank(re)&& "1".equals(String.valueOf(re.charAt(0)))){
			result.setCode("success");
			result.setMsg("[" + user.getUserCode()+ "]" +re.substring(2));
		}else
		{
			result.setCode("error");
			result.setMsg("[" + user.getUserCode()+ "]" + (StringUtil.isBlank(re)?"出错啦":re.substring(2)));
		}
		return result;
	}
	@Override
	public List<ServiceResult> userBatchCommonDML(List<User> ulist, int flag) {
		List<ServiceResult> relist = new ArrayList<ServiceResult>();
		for(User user : ulist){
			if( null  == user)continue;
			relist.add(userCommonDML(user, flag));
		}
		return relist;
	}
	@Override
	public ServiceResult selectUserDetailByCode(String userCode) {
		ServiceResult result = new ServiceResult();
		String re = "";
		try{
			User user = userMapper.selectUserDetailByCode(dkeycolumn,ukeycolumn, userCode);
			if( null == user ){
				result.setCode("error");
				result.setMsg("[" + userCode+ "] 用户不存在" );
				return result; 
			}
			result.setCode("success");
			result.setData(user);
		} catch(Exception e){
			e.printStackTrace();
			result.setCode("error");
			result.setMsg("[" + userCode + "] "+e.getMessage() );
			return result;
		}
		return result;
	}
	User getValidUserDetailByCode(String userCode){
		try{
			User user = userMapper.selectUserDetailByCode(dkeycolumn,ukeycolumn, userCode);
			if (user.getStatusCode() != 1) return null;
			return user;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public ServiceResult selectValidUserDetailListByRoot(String rootCode) {
		ServiceResult result = new ServiceResult();
		try {
			List<User> ulist = userMapper.selectValidUserDetailListByRoot(dkeycolumn,ukeycolumn,rootCode) ;
			result.setCode("success");
			result.setData(ulist);
		} catch (SQLException e) {			
			e.printStackTrace();
			result.setCode("error");
			result.setMsg("[" + rootCode + "] "+e.getMessage() );
			return result;
		}
		return result;
	}
	@Override
	public ServiceResult selectListBySearchWapper(SearchWapper<User> uSearch,boolean toPage) {
		ServiceResult result = new ServiceResult();
		
		try{
			SearchListExample sExp = new SearchListExample();
			User schbean =  uSearch.getCriteria();
			schbean.searchListExample(sExp);
		
			JSONArray sortarr = uSearch.getSort();
			JSONArray orderarr = uSearch.getOrder();
			if(null != sortarr){
				String orderby = org.ccps.common.db.util.PaginationUtil.getOrderBy(sortarr, orderarr);
				sExp.setOrderByClause(orderby);
			}
			Map<String ,Object> map = new HashMap<String,Object>();
			map.put("i_dkeycolumn", dkeycolumn);map.put("i_ukeycolumn", ukeycolumn);
			map.put("i_searchexample", sExp);
			//PageHelper pageHelper = PageUtil.oracle();
			if(toPage)PageHelper.startPage(uSearch.getStart(), uSearch.getSize());
			List<User> ulist = userMapper.selectUserDetailListBySearchWapper(map);		
			if(toPage){
				PageInfo<User> pageInfo = new PageInfo<User>(ulist);
				result.setP_order(orderarr!=null?orderarr.toString():null);
				result.setP_sort(sortarr!=null?sortarr.toString():null);
				result.setP_size(uSearch.getSize());
				result.setP_start(uSearch.getStart());
				result.setP_total(pageInfo.getTotal());
			}
			
			result.setCode("success");
			result.setData(ulist);
			return result;
		} catch (Exception e ){
			e.printStackTrace();
			result.setCode("error");
			result.setMsg("[" + JSONUtil.json().readAsString(uSearch) + "] "+e.getMessage() );
			return result;
		}
		
	}
	
}
