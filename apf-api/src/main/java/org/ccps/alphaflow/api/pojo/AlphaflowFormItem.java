package org.ccps.alphaflow.api.pojo;

public class AlphaflowFormItem {
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getType_id() {
		return type_id;
	}
	public void setType_id(Integer type_id) {
		this.type_id = type_id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	
	public String getDef_field_id() {
		return def_field_id;
	}
	public void setDef_field_id(String def_field_id) {
		this.def_field_id = def_field_id;
	}
	
	
	public String getKey_value_flag() {
		return key_value_flag;
	}
	public void setKey_value_flag(String key_value_flag) {
		this.key_value_flag = key_value_flag;
	}
	public String getKey_value_list() {
		return key_value_list;
	}
	public void setKey_value_list(String key_value_list) {
		this.key_value_list = key_value_list;
	}


	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	

	public Integer getSub_type_id() {
		return sub_type_id;
	}
	public void setSub_type_id(Integer sub_type_id) {
		this.sub_type_id = sub_type_id;
	}
	

	public Integer getAction_group_id() {
		return action_group_id;
	}
	public void setAction_group_id(Integer action_group_id) {
		this.action_group_id = action_group_id;
	}	

	public Integer getTemplate_item_id() {
		return template_item_id;
	}
	public void setTemplate_item_id(Integer template_item_id) {
		this.template_item_id = template_item_id;
	}


	private Integer id;
	//10:复选框,16:数据方阵
	private Integer type_id;
	private String item_name;
	private String def_field_id;
	//Y 来自系统基础数据 N 来自表单编辑填写
	private String key_value_flag;
	//如果key_value_flag=Y 则key_value_list表示catgory_id
	private String key_value_list;
	private Integer parent_id;
	//数据方阵 下拉组件中表示category_id
	private Integer sub_type_id;
	
	private Integer action_group_id;
	private Integer template_item_id;
}
