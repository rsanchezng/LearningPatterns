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

    public PDDLProperties() {
    }


    public Student getStudent() {
        return this.student;
    }

    public List<Subject> getSubjects() {
        return this.subjects;
    }

    public List<Theme> getThemes() {
        return this.themes;
    }

    public List<Subtheme> getSubthemes() {
        return this.subthemes;
    }

    public List<Activity> getActivites() {
        return this.activites;
    }

    public List<Resource> getResources() {
        return this.resources;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }

    public void setSubthemes(List<Subtheme> subthemes) {
        this.subthemes = subthemes;
    }

    public void setActivites(List<Activity> activites) {
        this.activites = activites;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof com.fime.pddl.PDDLProperties)) return false;
        final com.fime.pddl.PDDLProperties other = (com.fime.pddl.PDDLProperties) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$student = this.student;
        final java.lang.Object other$student = other.student;
        if (this$student == null ? other$student != null : !this$student.equals(other$student)) return false;
        final java.lang.Object this$subjects = this.subjects;
        final java.lang.Object other$subjects = other.subjects;
        if (this$subjects == null ? other$subjects != null : !this$subjects.equals(other$subjects)) return false;
        final java.lang.Object this$themes = this.themes;
        final java.lang.Object other$themes = other.themes;
        if (this$themes == null ? other$themes != null : !this$themes.equals(other$themes)) return false;
        final java.lang.Object this$subthemes = this.subthemes;
        final java.lang.Object other$subthemes = other.subthemes;
        if (this$subthemes == null ? other$subthemes != null : !this$subthemes.equals(other$subthemes)) return false;
        final java.lang.Object this$activites = this.activites;
        final java.lang.Object other$activites = other.activites;
        if (this$activites == null ? other$activites != null : !this$activites.equals(other$activites)) return false;
        final java.lang.Object this$resources = this.resources;
        final java.lang.Object other$resources = other.resources;
        if (this$resources == null ? other$resources != null : !this$resources.equals(other$resources)) return false;
        return true;
    }

    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof com.fime.pddl.PDDLProperties;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $student = this.student;
        result = result * PRIME + ($student == null ? 43 : $student.hashCode());
        final java.lang.Object $subjects = this.subjects;
        result = result * PRIME + ($subjects == null ? 43 : $subjects.hashCode());
        final java.lang.Object $themes = this.themes;
        result = result * PRIME + ($themes == null ? 43 : $themes.hashCode());
        final java.lang.Object $subthemes = this.subthemes;
        result = result * PRIME + ($subthemes == null ? 43 : $subthemes.hashCode());
        final java.lang.Object $activites = this.activites;
        result = result * PRIME + ($activites == null ? 43 : $activites.hashCode());
        final java.lang.Object $resources = this.resources;
        result = result * PRIME + ($resources == null ? 43 : $resources.hashCode());
        return result;
    }

    public java.lang.String toString() {
        return "PDDLProperties(student=" + this.student + ", subjects=" + this.subjects + ", themes=" + this.themes + ", subthemes=" + this.subthemes + ", activites=" + this.activites + ", resources=" + this.resources + ")";
    }
}
