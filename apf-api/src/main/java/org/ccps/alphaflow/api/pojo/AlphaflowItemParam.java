package org.ccps.alphaflow.api.pojo;
import java.util.List;

public class AlphaflowItemParam {

	
	public String getItemdef() {
		return itemdef;
	}



	public void setItemdef(String itemdef) {
		this.itemdef = itemdef;
	}



	public String getItemval() {
		return itemval;
	}



	public void setItemval(String itemval) {
		this.itemval = itemval;
	}



	public String getGriddef() {
		return griddef;
	}
	
	public void setGriddef(String griddef) {
		this.griddef = griddef;
	}

	public List<List<AlphaflowGridItemParam>> getGitems() {
		return gitems;
	}
	
	public void setGitems(List<List<AlphaflowGridItemParam>> gitems) {
		this.gitems = gitems;
	}
	
	public String getItemvalcode() {
		return itemvalcode;
	}

	public void setItemvalcode(String itemvalcode) {
		this.itemvalcode = itemvalcode;
	}

	

	private String itemdef;
	private String itemval;
	private String itemvalcode;
	private String griddef;
	private List<List<AlphaflowGridItemParam>> gitems;
	
	

	
}
