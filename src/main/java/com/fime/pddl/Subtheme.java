package com.fime.pddl;

import java.util.List;

public class Subtheme {

	private String subthemeName;
	private List<Activity> activities;

	public Subtheme(String subthemeName, List<Activity> activities) {
		super();
		this.subthemeName = subthemeName;
		this.activities = activities;
	}

	public String getThemeName() {
		return subthemeName;
	}

	public void setThemeName(String subthemeName) {
		this.subthemeName = subthemeName;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	@Override
	public String toString() {
		return 	subthemeName + "\n\n" + 
				activities.toString();
	}

}
