package com.fime.pddl;

import java.util.List;

public class Activity {

	private String activityIdentifier;
	private String activityName;
	private Subtheme parentSubtheme;
	private List<Object> dependencies;

	
	public Activity(String activityIdentifier, String activityName) {
		this.activityIdentifier = activityIdentifier;
		this.activityName = activityName;
	}

	public String getActivityIdentifier() {
		return activityIdentifier;
	}

	public void setActivityIdentifier(String activityIdentifier) {
		this.activityIdentifier = activityIdentifier;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Subtheme getParentSubtheme() {
		return parentSubtheme;
	}

	public void setParentSubtheme(Subtheme parentSubtheme) {
		this.parentSubtheme = parentSubtheme;
	}

	public List<Object> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Object> dependencies) {
		this.dependencies = dependencies;
	}
	
}
