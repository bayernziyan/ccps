package org.ccps.op.core;

import java.util.concurrent.ConcurrentHashMap;

public class MetaParamImpl {
	private ConcurrentHashMap<String, MetaParam> metamap = new ConcurrentHashMap<>();
	
	public void setMeta(String key,MetaParam value){
		metamap.put(key, value);
	}
	
	public MetaParam getMeta(String key){
		if(metamap.containsKey(key))
			return metamap.get(key);
		return null;
	}
	
	public String getMetaVal(String key){
		MetaParam conf = getMeta(key);
		if(null == conf) return null;
		return conf.getValue();
	}
	
	public void clearMeta(){
		metamap.clear();
		metamap = new ConcurrentHashMap<>();	
	}
}
