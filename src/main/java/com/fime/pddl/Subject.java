package com.fime.pddl;

import java.util.List;

public class Subject {
	
	private String subjectName;
	private List<Object> dependencies;
	
	public Subject(String subjectName) {
		this.subjectName = subjectName;
	}
	
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public List<Object> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Object> dependencies) {
		this.dependencies = dependencies;
	}
	
	
}
