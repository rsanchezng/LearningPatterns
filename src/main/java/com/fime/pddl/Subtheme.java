package com.fime.pddl;

public class Subtheme {

    private String name;
    private Theme parentTheme;
    private boolean done;
    private int maxGrade;
    private int studentScore;

    public Subtheme() {
    }

    public Subtheme(String name, Theme parentTheme, boolean done, int maxGrade,
                    int studentScore) {
		this.name = name;
		this.parentTheme = parentTheme;
		this.done = done;
		this.maxGrade = maxGrade;
		this.studentScore = studentScore;
	}

    public String getName() {
        return this.name;
    }

    public Theme getParentTheme() {
        return this.parentTheme;
    }

    public boolean isDone() {
        return this.done;
    }

    public int getMaxGrade() {
        return this.maxGrade;
    }

    public int getStudentScore() {
        return this.studentScore;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentTheme(Theme parentTheme) {
        this.parentTheme = parentTheme;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setMaxGrade(int maxGrade) {
        this.maxGrade = maxGrade;
    }

    public void setStudentScore(int studentScore) {
        this.studentScore = studentScore;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Subtheme)) return false;
        final Subtheme other = (Subtheme) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$name = this.name;
        final Object other$name = other.name;
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$parentTheme = this.parentTheme;
        final Object other$parentTheme = other.parentTheme;
        if (this$parentTheme == null ? other$parentTheme != null : !this$parentTheme.equals(other$parentTheme))
            return false;
        if (this.done != other.done) return false;
        if (this.maxGrade != other.maxGrade) return false;
        if (this.studentScore != other.studentScore) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Subtheme;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.name;
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $parentTheme = this.parentTheme;
        result = result * PRIME + ($parentTheme == null ? 43 : $parentTheme.hashCode());
        result = result * PRIME + (this.done ? 79 : 97);
        result = result * PRIME + this.maxGrade;
        result = result * PRIME + this.studentScore;
        return result;
    }

    public String toString() {
        return "Subtheme(name=" + this.name + ", parentTheme=" + this.parentTheme + ", done=" + this.done + ", maxGrade=" + this.maxGrade + ", studentScore=" + this.studentScore + ")";
    }
}
