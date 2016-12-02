package org.ccps.op.api.service;

import java.util.List;
import java.util.Map;

import org.ccps.op.api.dbmapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
	
	@Autowired
	private TestMapper testMapper;
	
	
	public void test(){
		List<Map<String,Object>> list= testMapper.getUsers("chenwj");
		System.out.println(list.size()+"@@@" + list);
	}
	
}
