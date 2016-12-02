package org.ccps.op.core;

public class ApplicationImpl extends MetaParamImpl {
	private Application app;
	
	public static ApplicationImpl impl(Application app){
		return new ApplicationImpl(app);
	}
	private ApplicationImpl(){}
	private ApplicationImpl(Application app){
		this.app = app;
	}
	@Override
	public void setMeta(String key, MetaParam value) {
		value.setBelongTo(app.getCorpId() + "-" +app.getAppId());
		super.setMeta(key, value);
	}
	@Override
	public String getMetaVal(String key) {
		return super.getMetaVal(key);
	}
}
