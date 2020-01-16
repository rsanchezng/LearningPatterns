package com.fime.pddl;
import java.util.List;

public class Theme {

	private String themeName;
	private List<Subtheme> subthemes;
	
	public Theme(String themeName, List<Subtheme> subthemes) {
		this.themeName = themeName;
		this.subthemes = subthemes;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public List<Subtheme> getSubthemes() {
		return subthemes;
	}

	public void setSubthemes(List<Subtheme> subthemes) {
		this.subthemes = subthemes;
	}
	
	@Override
	public String toString() {
		return  themeName + "\n\n" +
				subthemes.toString();
	}

}
