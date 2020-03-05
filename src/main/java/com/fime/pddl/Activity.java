package com.fime.pddl;

public class Activity {

	private String activityIdentifier;
	private String activityName;
	
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

}
