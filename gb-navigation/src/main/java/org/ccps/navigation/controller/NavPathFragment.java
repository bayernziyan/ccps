package org.ccps.navigation.controller;

import org.ccps.common.roca.core.PathFragment;

public enum NavPathFragment implements PathFragment {
	
	OPAPI("opapi"),
	APFAPI("apfapi"),
	LOGOUT("logout");
	
	private String name;

	private NavPathFragment(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see de.codecentric.moviedatabase.controller.PathFragment#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	
}
