package org.ccps.common.db.util;



import org.ccps.common.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PaginationUtil {
	private static final int DEF_PAGE_NUM = 1;
	private static final int DEF_PAGE_SIZE = 10;
	
	public static int getPageNum(JSONObject params){
		try{
		if(params!=null&&params.containsKey("start")){
			return Integer.parseInt(params.getString("start"));
		}
		}catch(Exception e){e.printStackTrace();}
		return DEF_PAGE_NUM;
	}
	
	public static int getPageSize(JSONObject params){
		try{
		if(params!=null&&params.containsKey("size")){
			return Integer.parseInt(params.getString("size"));
		}
		}catch(Exception e){e.printStackTrace();}
		return DEF_PAGE_SIZE;
	}
	
	public static String getOrderBy(JSONArray sortarr,String order){
		if(sortarr==null||sortarr.size()==0)return null;
		JSONArray orderarr = null;
		try{
		if(!StringUtil.isBlank(order))
			orderarr = JSONArray.fromObject(order);
		}catch(Exception e){e.printStackTrace();}
		StringBuilder sb = new StringBuilder();
		int i=0;
		for(Object sobj : sortarr){
			Object odobj = SearchUtil.getValByInd(i,orderarr);
			String stcol = String.valueOf(sobj);
			if(sb.length()>0)sb.append(" , ");
			sb.append(stcol).append(" ").append(odobj!=null?String.valueOf(odobj):"asc");
			i++;		
		}
		return sb.toString();
	}
	public static String getOrderBy(JSONArray sortarr,JSONArray orderarr){
		if(sortarr==null||sortarr.size()==0)return null;
		StringBuilder sb = new StringBuilder();
		int i=0;
		for(Object sobj : sortarr){
			Object odobj = SearchUtil.getValByInd(i,orderarr);
			String stcol = String.valueOf(sobj);
			if(sb.length()>0)sb.append(" , ");
			sb.append(stcol).append(" ").append(odobj!=null?String.valueOf(odobj):"asc");
			i++;		
		}
		return sb.toString();
	}
	
}
