package org.ccps.navigation.controller;

import org.ccps.common.roca.core.Relation;

/**
 * This is our internal API for the view. Links are identified by relations. Of course not every entity has a
 * link for every relation listed here, it always depends on the circumstances.
 * 
 * @author tobias.flohre
 */
public enum NavRelation implements Relation {
	
	SELF("self"), 
	OPAPI("opapi"),
	APFAPI("apfapi"),

	SEARCH("search"),
	LOGOUT("logout");

	
	private String name;

	private NavRelation(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see de.codecentric.moviedatabase.controller.Relation#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	
}
