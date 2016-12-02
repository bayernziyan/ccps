package org.ccps.alphaflow.api.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("alphaflowTaskFilter")
public class AlphaflowTaskBean {

	public Integer getTask_id() {
		return task_id;
	}
	public void setTask_id(Integer task_id) {
		this.task_id = task_id;
	}
	public String getTask_name() {
		return task_name;
	}
	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}
	public Integer getStatus_id() {
		return status_id;
	}
	public void setStatus_id(Integer status_id) {
		this.status_id = status_id;
	}
	public Integer getTask_level() {
		return task_level;
	}
	public void setTask_level(Integer task_level) {
		this.task_level = task_level;
	}
	public Integer getAssignee_type() {
		return assignee_type;
	}
	public void setAssignee_type(Integer assignee_type) {
		this.assignee_type = assignee_type;
	}
	public Integer getAssignee() {
		return assignee;
	}
	public void setAssignee(Integer assignee) {
		this.assignee = assignee;
	}
	public String getAssignee_name() {
		return assignee_name;
	}
	public void setAssignee_name(String assignee_name) {
		this.assignee_name = assignee_name;
	}
	public Integer getWfId() {
		return wfId;
	}
	public void setWfId(Integer wfId) {
		this.wfId = wfId;
	}
	public Integer getPrior_task_id() {
		return prior_task_id;
	}
	public void setPrior_task_id(Integer prior_task_id) {
		this.prior_task_id = prior_task_id;
	}
	public Integer getEnd_task_id() {
		return end_task_id;
	}
	public void setEnd_task_id(Integer end_task_id) {
		this.end_task_id = end_task_id;
	}
	public Integer getEnd_task_level() {
		return end_task_level;
	}
	public void setEnd_task_level(Integer end_task_level) {
		this.end_task_level = end_task_level;
	}
	public String getEnd_task_name() {
		return end_task_name;
	}
	public void setEnd_task_name(String end_task_name) {
		this.end_task_name = end_task_name;
	}
	public Integer getEnd_task_type_id() {
		return end_task_type_id;
	}
	public void setEnd_task_type_id(Integer end_task_type_id) {
		this.end_task_type_id = end_task_type_id;
	}
	public Integer getEnd_task_status_id() {
		return end_task_status_id;
	}
	public void setEnd_task_status_id(Integer end_task_status_id) {
		this.end_task_status_id = end_task_status_id;
	}
	public Integer getEnd_task_assignee_type() {
		return end_task_assignee_type;
	}
	public void setEnd_task_assignee_type(Integer end_task_assignee_type) {
		this.end_task_assignee_type = end_task_assignee_type;
	}
	public Integer getEnd_task_assignee() {
		return end_task_assignee;
	}
	public void setEnd_task_assignee(Integer end_task_assignee) {
		this.end_task_assignee = end_task_assignee;
	}
	public String getEnd_task_assignee_name() {
		return end_task_assignee_name;
	}
	public void setEnd_task_assignee_name(String end_task_assignee_name) {
		this.end_task_assignee_name = end_task_assignee_name;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
	public String getApprove_code() {
		return approve_code;
	}
	public void setApprove_code(String approve_code) {
		this.approve_code = approve_code;
	}
	public String getApprove_comment() {
		return approve_comment;
	}
	public void setApprove_comment(String approve_comment) {
		this.approve_comment = approve_comment;
	}
	public String getWfName() {
		return wfName;
	}
	public void setWfName(String wfName) {
		this.wfName = wfName;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getInit_id() {
		return init_id;
	}
	public void setInit_id(String init_id) {
		this.init_id = init_id;
	}



	public Date getAssign_date() {
		return assign_date;
	}
	public void setAssign_date(Date assign_date) {
		this.assign_date = assign_date;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}



	public String getAssigner_name() {
		return assigner_name;
	}
	public void setAssigner_name(String assigner_name) {
		this.assigner_name = assigner_name;
	}



	private Integer task_id;
	private String task_name;
	private Integer status_id;
	private String status;
	private Integer task_level;
	private Integer assignee_type;
	private Integer assignee;
	private Date assign_date;
	private Date create_date;
	private String group_name;
	private String assignee_name;
	private String assigner_name;
	private Integer wfId;
	private String init_id;
	private String  wfName;
	private Integer prior_task_id;
	private Integer end_task_id;
	private Integer end_task_level;
	private String end_task_name;
	private Integer end_task_type_id;
	private Integer end_task_status_id;
	private Integer end_task_assignee_type;
	private Integer end_task_assignee;
	private String end_task_assignee_name;
	private String approve_code;
	private String approve_comment;
	
}
