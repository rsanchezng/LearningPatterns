package com.fime.pddl;

import java.util.List;

public class Resource {
	
	private String resourceName;
	private Activity parentActivity;
	private List<Object> dependencies;

	
	public Resource(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Activity getParentActivity() {
		return parentActivity;
	}

	public void setParentActivity(Activity parentActivity) {
		this.parentActivity = parentActivity;
	}

	public List<Object> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Object> dependencies) {
		this.dependencies = dependencies;
	}
	
}
