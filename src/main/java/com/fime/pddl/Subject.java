package com.fime.pddl;
import java.util.List;

public class Subject {
	
	private String subjectName;
	private List<Theme> themes;
		
	public Subject(String subjectName, List<Theme> themes) {
		super();
		this.subjectName = subjectName;
		this.themes = themes;
	}
	
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public List<Theme> getThemes() {
		return themes;
	}
	public void setThemes(List<Theme> themes) {
		this.themes = themes;
	}

	@Override
	public String toString() {
		return  subjectName + "\n\n" +
				themes.toString();
	}
	
}
