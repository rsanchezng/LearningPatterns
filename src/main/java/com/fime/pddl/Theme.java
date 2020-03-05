package com.fime.pddl;

import java.util.List;

public class Theme {

	private String themeName;
	private Subject parentSubject;
	private List<Object> dependencies;

	
	public Theme(String themeName) {
		this.themeName = themeName;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public Subject getParentSubject() {
		return parentSubject;
	}

	public void setParentSubject(Subject parentSubject) {
		this.parentSubject = parentSubject;
	}

	public List<Object> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Object> dependencies) {
		this.dependencies = dependencies;
	}
	
	

}
