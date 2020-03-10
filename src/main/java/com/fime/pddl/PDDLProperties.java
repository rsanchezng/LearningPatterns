package com.fime.pddl;

import java.util.List;

import lombok.Data;

@Data
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

}
