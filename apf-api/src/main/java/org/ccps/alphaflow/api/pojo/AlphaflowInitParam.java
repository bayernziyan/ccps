package org.ccps.alphaflow.api.pojo;
import java.util.List;

public class AlphaflowInitParam {

	public String getWfdef() {
		return wfdef;
	}
	public void setWfdef(String wfdef) {
		this.wfdef = wfdef;
	}
	public String getInituser() {
		return inituser;
	}
	public void setInituser(String inituser) {
		this.inituser = inituser;
	}
	public List<AlphaflowItemParam> getParams() {
		return params;
	}
	public void setParams(List<AlphaflowItemParam> params) {
		this.params = params;
	}
	
	private String wfdef;
	private String inituser;
	private List<AlphaflowItemParam> params;
	
	
}
