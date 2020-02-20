package com.fime.pddl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PDDLProperties {
	
	List<Subject> subjectList;
	
	List<String> subjects;
	List<String> themes;
	List<String> subthemes;
	List<String> activites;
	List<String> resources;
	
	public PDDLProperties(List<Subject> subjects) {
		this.subjectList = subjects;
		setListsOfProperties();
	}
	
	private void setListsOfProperties() {

		subjects = new ArrayList<>();
		themes = new ArrayList<>();
		subthemes = new ArrayList<>();
		activites = new ArrayList<>();
		resources = new ArrayList<>();

		for (Subject subject : subjectList) {
			subjects.add(subject.getSubjectName());
			
			for (Theme theme : subject.getThemes()) {
				themes.add(theme.getThemeName());
			
				for (Subtheme subtheme : theme.getSubthemes()) {
					subthemes.add(subtheme.getThemeName());
				
					for (Activity activity : subtheme.getActivities()) {
						activites.add(activity.getActivityName());
					
						for (Resource resource : activity.getResources()) {
							resources.add(resource.getResourceName());
						}
					}
				}
			}
		}
	}

}
