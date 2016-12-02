package org.ccps.alphaflow.api.pojo;

import org.ccps.common.util.StringUtil;

public class UserBean {
	
	public String getFullDeptNameByLevel(){
		if(this.fullDeptName==null||this.fullDeptName.isEmpty())return "";
		String[] names = fullDeptName.split("--");
		int size = names.length;
		String rename = fullDeptName;
		if(size>2){
			rename = "";
			for(int i=size-2;i<size;i++){
				if(!rename.isEmpty())rename+="--";
				rename += names[i];
			}
		}
		return rename;
	}
	
	public String getUserFullName(){
		String deptfull = getFullDeptNameByLevel();
		if(StringUtil.isBlank(deptfull))return "";
		return "P,"+myWsId+"-"+deptfull+"--"+userName;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getUserSkey() {
		return userSkey;
	}
	public void setUserSkey(Integer userSkey) {
		this.userSkey = userSkey;
	}
	public Integer getMyWsId() {
		return myWsId;
	}
	public void setMyWsId(Integer myWsId) {
		this.myWsId = myWsId;
	}
	public String getFullDeptName() {
		return fullDeptName;
	}
	public void setFullDeptName(String fullDeptName) {
		this.fullDeptName = fullDeptName;
	}
	
	private String  userId;
	private String  userName;
	private Integer userSkey;
	private Integer myWsId;
	private String  fullDeptName;
	
}
