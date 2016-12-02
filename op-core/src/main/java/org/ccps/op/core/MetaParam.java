package org.ccps.op.core;

public class MetaParam {
	private String belongTo;
	private String key;
	private String value;
	private long timestamp;
	private long invalidtime;
	
	
	public String getBelongTo(){
		return belongTo;
	}
	public void setBelongTo(String belongTo){
		this.belongTo = belongTo;
	}
	public String getKey(){
		return key;
	}
	
	public boolean isExpired(){
		return System.currentTimeMillis() > invalidtime;
	}
	
	public String getValue(){
		return !isExpired()?this.value:null;
	}
	
	public MetaParam(String belongTo,String key,String value){
		this.belongTo = belongTo;
		this.key = key;
		this.value = value;
		this.timestamp = System.currentTimeMillis();
		invalidtime = 0l;
	}
	
	public MetaParam(String belongTo,String key,String value,long expiretime){
		this.belongTo = belongTo;
		this.key = key;
		this.value = value;
		this.timestamp = System.currentTimeMillis();
		if(expiretime>20000)
			this.invalidtime = this.timestamp + expiretime - 5000l;
		else 
			this.invalidtime = this.timestamp + expiretime;
	}
}
