package org.ccps.organization.api.common;

import org.springframework.hateoas.ResourceSupport;

public class ServiceResult extends ResourceSupport  {
	private String code;
	private String msg;
	
	private Object data;
	private long p_total;
	private int p_start;
	private int p_size;
	private String p_sort;
	private String p_order;
	
	
	
	
	
	
	public long getP_total() {
		return p_total;
	}
	public void setP_total(long p_total) {
		this.p_total = p_total;
	}
	public int getP_start() {
		return p_start;
	}
	public void setP_start(int p_start) {
		this.p_start = p_start;
	}
	public int getP_size() {
		return p_size;
	}
	public void setP_size(int p_size) {
		this.p_size = p_size;
	}
	public String getP_sort() {
		return p_sort;
	}
	public void setP_sort(String p_sort) {
		this.p_sort = p_sort;
	}
	public String getP_order() {
		return p_order;
	}
	public void setP_order(String p_order) {
		this.p_order = p_order;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
