package org.ccps.alphaflow.api.common;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ServiceExcuteResult {
	private JSONArray paramre ;
	private Object result;
	
	public  ServiceExcuteResult setParams(JSONObject obj){
		if(paramre==null) paramre = new JSONArray();
		paramre.add(obj);
		return this;
	}
	
	public ServiceExcuteResult setResult(Object result){
		this.result = result;
		return this;
	}

	public JSONArray getParamre() {
		return paramre;
	}
	public String getParamreStr() {
		return paramre!=null?paramre.toString():null;
	}
	public Object getResult() {
		return result;
	}
	
	
}
