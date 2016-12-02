package org.ccps.op.core;

import net.sf.json.JSONObject;

public class MenuImpl extends MetaParamImpl {
	private Menu menu;
	public static MenuImpl impl(Menu menu){
		return new MenuImpl(menu);
	}
	private MenuImpl(){}
	private MenuImpl(Menu menu){
		this.menu = menu;
	}
	
	
}
