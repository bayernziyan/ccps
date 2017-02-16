package org.ccps.organization.api.pojo;

import java.sql.Date;

import org.ccps.common.db.util.SearchInterface;
import org.ccps.common.db.util.SearchListExample;
import org.ccps.common.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class User implements SearchInterface {

	/**
	 *  p_psn_code in varchar2,
   p_psn_name in varchar2,
   p_def_pwd  in varchar2,
   p_sex in varchar2,
   p_ssn in varchar2,
   p_dept_code IN varchar2,
   p_birthdate in varchar2,
   p_enterunitdate in varchar2,
   p_mobile in varchar2,
   p_mail in varchar2,

   p_statuscode in number,
   p_depttype   in number,
	 */
	private String userCode;
	private String userName;
	@JsonIgnore
	private String defPwd;
	/**
	 * w m
	 */
	private String sex;
	private String ssn;
	private String deptCode;
	private String birthDate;
	private String enterUnitDate;
	private String mobilePhone;
	private String mailAddr;
	private int statusCode;
	private int deptType = -1;
	private Date createDate;
	
	private String createDate_s;
	private String createDate_e;
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDefPwd() {
		return defPwd;
	}
	public void setDefPwd(String defPwd) {
		this.defPwd = defPwd;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getEnterUnitDate() {
		return enterUnitDate;
	}
	public void setEnterUnitDate(String enterUnitDate) {
		this.enterUnitDate = enterUnitDate;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getMailAddr() {
		return mailAddr;
	}
	public void setMailAddr(String mailAddr) {
		this.mailAddr = mailAddr;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public int getDeptType() {
		return deptType;
	}
	public void setDeptType(int deptType) {
		this.deptType = deptType;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateDate_s() {
		return createDate_s;
	}
	public void setCreateDate_s(String createDate_s) {
		this.createDate_s = createDate_s;
	}
	public String getCreateDate_e() {
		return createDate_e;
	}
	public void setCreateDate_e(String createDate_e) {
		this.createDate_e = createDate_e;
	}
	//SearchListExample sExp = new SearchListExample()
	public SearchListExample searchListExample(SearchListExample sExp){
		SearchListExample.Criteria c = sExp.createCriteria();
		
		if(!StringUtil.isBlank(userCode)){
			c.andColLike("userCode", "%"+userCode+"%");
		}
		if(!StringUtil.isBlank(userName)){
			c.andColLike("userName", "%"+userName+"%");
		}
		if(!StringUtil.isBlank(sex)){
			c.addCriterion("sex =", sex, "sex");
		}
		if(!StringUtil.isBlank(ssn)){
			c.addCriterion("ssn =", ssn, "ssn");
		}
		if(!StringUtil.isBlank(deptCode)){
			c.addCriterion("deptCode =", deptCode, "deptCode");
		}
		if(!StringUtil.isBlank(birthDate)){
			c.andColLike("birthDate", "%"+birthDate+"%");
		}
		if(!StringUtil.isBlank(enterUnitDate)){
			c.andColLike("enterUnitDate", "%"+enterUnitDate+"%");
		}
		if(!StringUtil.isBlank(mobilePhone)){
			c.andColLike("mobilePhone", "%"+mobilePhone+"%");
		}
		if(!StringUtil.isBlank(mailAddr)){
			c.andColLike("mailAddr", "%"+mailAddr+"%");
		}
		if(statusCode>0){
			c.addCriterion("statusCode=", statusCode,"statusCode");
		}
		if(deptType>-1){
			c.addCriterion("deptType=", deptType,"deptType");
		}
		/*if(createDate!=null){
			c.addCriterion("createDate=", createDate,"createDate");
		}*/
		if(!StringUtil.isBlank(createDate_s)){
			c.addCriterion("createDate >=", "to_date('"+createDate_s+"','yyyy-mm-dd hh:mi:ss')","createDate");
		}
		if(!StringUtil.isBlank(createDate_e)){
			c.addCriterion("createDate <=", "to_date('"+createDate_e+"','yyyy-mm-dd hh:mi:ss')","createDate");
		}
		return sExp;		
	}
	
	
	
}
