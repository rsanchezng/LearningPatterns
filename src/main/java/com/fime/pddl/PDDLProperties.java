package com.fime.pddl;

import java.util.List;

public class PDDLProperties {

	private Student student;
	private List<Subject> subjects;
	private List<Theme> themes;
	private List<Subtheme> subthemes;
	private List<Activity> activites;
	private List<Resource> resources;
	
	public PDDLProperties(Student student, List<Subject> subjects, List<Theme> themes, List<Subtheme> subthemes,
			List<Activity> activites, List<Resource> resources) {
		super();
		this.student = student;
		this.subjects = subjects;
		this.themes = themes;
		this.subthemes = subthemes;
		this.activites = activites;
		this.resources = resources;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public List<Theme> getThemes() {
		return themes;
	}

	public void setThemes(List<Theme> themes) {
		this.themes = themes;
	}

	public List<Subtheme> getSubthemes() {
		return subthemes;
	}

	public void setSubthemes(List<Subtheme> subthemes) {
		this.subthemes = subthemes;
	}

	public List<Activity> getActivites() {
		return activites;
	}

	public void setActivites(List<Activity> activites) {
		this.activites = activites;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
	
}
