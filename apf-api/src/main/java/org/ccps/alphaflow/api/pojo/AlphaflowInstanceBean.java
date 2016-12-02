package org.ccps.alphaflow.api.pojo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AlphaflowInstanceBean {
	
	public Integer getWfId() {
		return wfId;
	}
	public void setWfId(Integer wfId) {
		this.wfId = wfId;
	}
	public Integer getFormId() {
		return formId;
	}
	public void setFormId(Integer formId) {
		this.formId = formId;
	}
	public Integer getFormDataId() {
		return formDataId;
	}
	public void setFormDataId(Integer formDataId) {
		this.formDataId = formDataId;
	}	
	public Integer getInitUserId() {
		return initUserId;
	}
	public void setInitUserId(Integer initUserId) {
		this.initUserId = initUserId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSub_category() {
		return sub_category;
	}
	public void setSub_category(String sub_category) {
		this.sub_category = sub_category;
	}
	public Integer getStatus_id() {
		return status_id;
	}
	public void setStatus_id(Integer status_id) {
		this.status_id = status_id;		
	}
	public String getWfName() {
		return wfName;
	}
	public void setWfName(String wfName) {
		this.wfName = wfName;
	}
	public String getWfCode() {
		return wfCode;
	}
	public void setWfCode(String wfCode) {
		this.wfCode = wfCode;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public List<Integer> getCurrent_taskid() {
		return current_taskid;
	}
	public void setCurrent_taskid(List<Integer> current_taskid) {
		this.current_taskid = current_taskid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	private Integer wfId;
	private Integer formId;
	private Integer formDataId;
	private Integer initUserId;
	private String  category;
	private String  sub_category;
	@JsonIgnore
	private Integer status_id;
	private String  status;

	private String  wfName;
	private String  wfCode;
	private Date 	createDate;
	
	private List<Integer> current_taskid;
	
}
