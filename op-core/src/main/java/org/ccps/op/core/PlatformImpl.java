package org.ccps.op.core;

public class PlatformImpl extends MetaParamImpl {
	private Platform pfm;
	

	@Override
	public void setMeta(String key, MetaParam value) {
		value.setBelongTo(pfm.getCorpId());
		super.setMeta(key, value);
	}
	@Override
	public String getMetaVal(String key) {
		return super.getMetaVal(key);
	}
	
	PlatformImpl(){}
	public PlatformImpl(Platform pf){
		this.pfm = pf;
	}
	public void setPlatform(Platform pf){
		this.pfm = pf;
		clearMeta();
	}
	public Platform getPlatform(){
		return this.pfm;
	}
}
