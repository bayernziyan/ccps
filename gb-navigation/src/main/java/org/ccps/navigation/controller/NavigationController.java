package org.ccps.navigation.controller;

import static org.ccps.common.roca.core.LinkBuilder.linkTo;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/navigation")
public class NavigationController{
	
	private String apiBaseUrl;

	
	public NavigationController(String apiBaseUrl) {
		this.apiBaseUrl = apiBaseUrl;	
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getNavigation(Model model, @RequestParam String searchUrl, @RequestParam String active, @RequestParam(required = false) String searchString) {
		NavigationResource navResource = new NavigationResource(active, searchString != null?searchString:"");
		navResource.add(linkTo(apiBaseUrl).path(NavPathFragment.OPAPI).withRel(NavRelation.OPAPI));		
		navResource.add(linkTo(apiBaseUrl).path(NavPathFragment.OPAPI).withRel(NavRelation.OPAPI));		
		/*navResource.add(linkTo(shopBaseUrl).withRel(NavRelation.SHOP));
		navResource.add(linkTo(actorsBaseUrl).path(NavPathFragment.ACTORS).withRel(NavRelation.ACTORS));*/
		navResource.add(linkTo(apiBaseUrl).path(NavPathFragment.LOGOUT).withRel(NavRelation.LOGOUT));
		navResource.add(linkTo(searchUrl).withRel(NavRelation.SEARCH));
		model.addAttribute("navResource", navResource);
		return "navigation/navigation";
	}
	
}
