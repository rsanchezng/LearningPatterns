package com.fime.pddl;
import java.util.List;

public class Activity {

	private String activityIdentifier;
	private String activityName;
	private List<Resource> resources;
	
	public Activity(String activityIdentifier, String activityName, List<Resource> resources) {
		this.activityIdentifier = activityIdentifier;
		this.activityName = activityName;
		this.resources = resources;
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

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	@Override
	public String toString() {
		return 	activityName + "\n\n" +
				resources.toString();
	}
	
}
