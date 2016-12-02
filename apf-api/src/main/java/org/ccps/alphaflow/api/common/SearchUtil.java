package org.ccps.alphaflow.api.common;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SearchUtil {

	public static String getSearchVal(String paramName,JSONObject param){
		if(param!=null&&param.containsKey(paramName)){
			return param.getString(paramName);
		}
		return "";
	}
	
	public static Object getValByInd(int ind,JSONArray arr){
		if(arr==null||arr.size()==0)return null;
		if(arr.size()>ind)
			return arr.get(ind);
		return null;
	}
}
