package org.ccps.op.core;

public class Menu {
	private int index;
	private int parentInd;
	private String name;
	private String type;
	private String key;
	
	public Menu(int ind,int parentInd,String name,String type,String key){
		this.index = ind;
		this.parentInd = parentInd;
		this.name = name;
		this.type = type;
		this.key = key;
	}
	
	
	
	public int getIndex() {
		return index;
	}



	public int getParentInd() {
		return parentInd;
	}



	public String getName() {
		return name;
	}



	public String getType() {
		return type;
	}



	public String getKey() {
		return key;
	}



	public boolean isFolder(){
		return parentInd == 0;
	}
	
}
