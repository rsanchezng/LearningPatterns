package com.fime.pddl;

public class Resource {
	
	public Resource(String resourceName) {
		this.resourceName = resourceName;
	}

	private String resourceName;

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
		
}
