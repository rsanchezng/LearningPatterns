package com.fime.pddl;

import java.util.List;

public class Subtheme {

	private String subthemeName;
	private Theme parentTheme;
	private List<Object> dependencies;


	public Subtheme(String subthemeName) {
		super();
		this.subthemeName = subthemeName;
	}

	public String getThemeName() {
		return subthemeName;
	}

	public void setThemeName(String subthemeName) {
		this.subthemeName = subthemeName;
	}

	public String getSubthemeName() {
		return subthemeName;
	}

	public void setSubthemeName(String subthemeName) {
		this.subthemeName = subthemeName;
	}

	public Theme getParentTheme() {
		return parentTheme;
	}

	public void setParentTheme(Theme parentTheme) {
		this.parentTheme = parentTheme;
	}

	public List<Object> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Object> dependencies) {
		this.dependencies = dependencies;
	}
	
	
}
