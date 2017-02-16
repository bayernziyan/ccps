package org.ccps.organization.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.ccps.common.db.util.SearchWapper;
import org.ccps.organization.api.common.ServiceResult;
import org.ccps.organization.api.pojo.Department;
import org.ccps.organization.api.pojo.User;
import org.ccps.organization.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST )
	public HttpEntity<ServiceResult> addOne(@RequestBody User user){
		return new HttpEntity<>(userService.userCommonDML(user, 1));
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST )
	public HttpEntity<ServiceResult> updateOne(@RequestBody User user){
		return new HttpEntity<>(userService.userCommonDML(user, 2));
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST )
	public HttpEntity<ServiceResult> deleteOne(@RequestParam(value="userCode",required=true) String userCode){
		User user = new User();
		user.setUserCode(userCode);;user.setStatusCode(2);
		return new HttpEntity<>(userService.userCommonDML(user, 3));
	}
	
	
	@RequestMapping(value = "/multi/add", method = RequestMethod.POST )
	public HttpEntity<List<ServiceResult>> addMulti(@RequestBody List<User> users){
		return new HttpEntity<>(userService.userBatchCommonDML(users, 1));
	}
	
	@RequestMapping(value = "/multi/update", method = RequestMethod.POST )
	public HttpEntity<List<ServiceResult>> updateMulti(@RequestBody List<User> users){
		return new HttpEntity<>(userService.userBatchCommonDML(users, 2));
	}
	
	@RequestMapping(value = "/multi/delete", method = RequestMethod.POST )
	public HttpEntity<List<ServiceResult>> deleteMulti(@RequestBody List<String> userCodes){
		List<User> users = new ArrayList<User>();
		for(String code : userCodes){
			User user = new User();
			user.setUserCode(code);user.setStatusCode(2);
			users.add(user);
		}
		return new HttpEntity<>(userService.userBatchCommonDML(users, 3));
	}
	
	@RequestMapping(value = "/detail", method = {RequestMethod.GET, RequestMethod.POST})
	public HttpEntity<ServiceResult> selectOne(@RequestParam(value="userCode",required=true) String userCode){
		return new HttpEntity<>(userService.selectUserDetailByCode(userCode));
	}
	
	
	@RequestMapping(value = "/list/root", method =  {RequestMethod.GET, RequestMethod.POST} )
	public HttpEntity<ServiceResult> selectListByRoot(@RequestParam(value="rootCode",required=false,defaultValue="0") String rootCode){
		return new HttpEntity<>(userService.selectValidUserDetailListByRoot(rootCode));
	}
	@RequestMapping(value = "/list/search", method = RequestMethod.POST )
	public HttpEntity<ServiceResult> selectListBySearchWapper(@RequestParam(value="page",required=false,defaultValue="") String page,@RequestBody SearchWapper<User> uSearch){
		return new HttpEntity<>(userService.selectListBySearchWapper(uSearch, "true".equalsIgnoreCase(page)));
	}
}
