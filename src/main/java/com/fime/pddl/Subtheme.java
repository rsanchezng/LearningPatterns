package com.fime.pddl;
import java.util.List;

public class Subtheme {

	private String subthemeName;
	private List<Activity> activities;

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

}
