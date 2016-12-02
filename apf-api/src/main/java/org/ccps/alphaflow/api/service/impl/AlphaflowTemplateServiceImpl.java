package org.ccps.alphaflow.api.service.impl;

import java.util.List;

import org.ccps.alphaflow.api.common.*;
import org.ccps.alphaflow.api.dbmapper.AlphaflowTemplateMapper;
import org.ccps.alphaflow.api.pojo.*;
import org.ccps.alphaflow.api.service.AlphaflowTemplateService;
import org.ccps.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class AlphaflowTemplateServiceImpl implements AlphaflowTemplateService {
	@Autowired
	private AlphaflowTemplateMapper alphaflowTemplateMapper;
	@Override
	public ServiceExcuteResult getAlphaflowTemplateList(JSONObject params) throws Exception {
		ServiceExcuteResult svcre = new ServiceExcuteResult();
		//name   category  sub_category   inituser    createdate_s    createdate_e
		
		try{
			int pageNum = PaginationUtil.getPageNum(params);
			int pageSize = PaginationUtil.getPageSize(params);		
			
			SearchListExample sExp = new SearchListExample();
			SearchListExample.Criteria c = sExp.createCriteria();
			
			String sort = SearchUtil.getSearchVal("sort", params);
			String order = StringUtil.isBlank(SearchUtil.getSearchVal("order", params),"asc");		
			if(!StringUtil.isBlank(sort)){
				try{
					JSONArray sortarr = JSONArray.fromObject(sort);
					String orderby = PaginationUtil.getOrderBy(sortarr, order);
					sExp.setOrderByClause(orderby);
				}catch(Exception e){e.printStackTrace();}
			}	
			
			String templateName = SearchUtil.getSearchVal("templateName", params);
			if(!StringUtil.isBlank(templateName)){			
				c.andColLike("templateName", templateName)	;	
			}			
			String categoryval = SearchUtil.getSearchVal("category", params);
			if(!StringUtil.isBlank(categoryval)){
				
				c.addCriterion("category =", categoryval, "category");
				
			}
			String sub_categoryval = SearchUtil.getSearchVal("sub_category", params);
			if(!StringUtil.isBlank(sub_categoryval)){
				c.addCriterion("sub_category =", sub_categoryval, "sub_category");				
			}
			String createdate_s = SearchUtil.getSearchVal("create_date_s", params);
			String createdate_e = SearchUtil.getSearchVal("create_date_e", params);
			if(!StringUtil.isBlank(createdate_s)){
				c.addCriterion("createDate >= ","to_date('"+createdate_s+"','yyyy-mm-dd')","createDate_s");			
			}
			if(!StringUtil.isBlank(createdate_e)){
				c.addCriterion("createDate <= ","to_date('"+createdate_e+"','yyyy-mm-dd')","createDate_e");			
			}
			PageHelper.startPage(pageNum, pageSize);
			
			List<AlphaflowTemplateBean> tmplist = alphaflowTemplateMapper.getAlphaflowTemplateList(sExp);
			PageInfo<AlphaflowTemplateBean> pageInfo = new PageInfo<AlphaflowTemplateBean>(tmplist);
			JSONObject reobj = new JSONObject();
			
			reobj.put("total", pageInfo.getTotal());
			reobj.put("start", pageNum);
			reobj.put("size",  pageSize);
			if(!StringUtil.isBlank(sort)){
				reobj.put("sort", sort);
				reobj.put("order", order);
			}
			reobj.put("data", JsonUtils.objectToJson(tmplist));		
			svcre.setResult(reobj);
		}catch(Exception e){
			e.printStackTrace();
			throw new RestException(e);
		}
		return svcre;	
		
	}

}
