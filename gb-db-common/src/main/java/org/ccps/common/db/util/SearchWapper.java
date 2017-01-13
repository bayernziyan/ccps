package org.ccps.common.db.util;

import net.sf.json.JSONArray;

public class SearchWapper<T extends SearchInterface> {
	
	private T criteria;
	private int start=1;
	private int size=10;
	private JSONArray sort;
	private JSONArray order;
	
	public T getCriteria() {
		return criteria;
	}
	public void setCriteria(T criteria) {
		this.criteria = criteria;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public JSONArray getSort() {
		return sort;
	}
	public void setSort(JSONArray sort) {
		this.sort = sort;
	}
	public JSONArray getOrder() {
		return order;
	}
	public void setOrder(JSONArray order) {
		this.order = order;
	}
	
	
	

}
