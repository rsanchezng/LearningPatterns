package com.fime.pddl;

public class Subtheme {

    private String subthemeName;
    private Theme parentTheme;
    //	private List<Object> dependencies;
    private boolean subthemeDone;
    private int maxGradeSubtheme;
    private int studentScore;

    public Subtheme() {
    }

    public String getSubthemeName() {
        return this.subthemeName;
    }

    public Theme getParentTheme() {
        return this.parentTheme;
    }

    public boolean isSubthemeDone() {
        return this.subthemeDone;
    }

    public int getMaxGradeSubtheme() {
        return this.maxGradeSubtheme;
    }

    public int getStudentScore() {
        return this.studentScore;
    }

    public void setSubthemeName(String subthemeName) {
        this.subthemeName = subthemeName;
    }

    public void setParentTheme(Theme parentTheme) {
        this.parentTheme = parentTheme;
    }

    public void setSubthemeDone(boolean subthemeDone) {
        this.subthemeDone = subthemeDone;
    }

    public void setMaxGradeSubtheme(int maxGradeSubtheme) {
        this.maxGradeSubtheme = maxGradeSubtheme;
    }

    public void setStudentScore(int studentScore) {
        this.studentScore = studentScore;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Subtheme)) return false;
        final Subtheme other = (Subtheme) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$subthemeName = this.subthemeName;
        final Object other$subthemeName = other.subthemeName;
        if (this$subthemeName == null ? other$subthemeName != null : !this$subthemeName.equals(other$subthemeName))
            return false;
        final Object this$parentTheme = this.parentTheme;
        final Object other$parentTheme = other.parentTheme;
        if (this$parentTheme == null ? other$parentTheme != null : !this$parentTheme.equals(other$parentTheme))
            return false;
        if (this.subthemeDone != other.subthemeDone) return false;
        if (this.maxGradeSubtheme != other.maxGradeSubtheme) return false;
        if (this.studentScore != other.studentScore) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Subtheme;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $subthemeName = this.subthemeName;
        result = result * PRIME + ($subthemeName == null ? 43 : $subthemeName.hashCode());
        final Object $parentTheme = this.parentTheme;
        result = result * PRIME + ($parentTheme == null ? 43 : $parentTheme.hashCode());
        result = result * PRIME + (this.subthemeDone ? 79 : 97);
        result = result * PRIME + this.maxGradeSubtheme;
        result = result * PRIME + this.studentScore;
        return result;
    }

    public String toString() {
        return "Subtheme(subthemeName=" + this.subthemeName + ", parentTheme=" + this.parentTheme + ", subthemeDone=" + this.subthemeDone + ", maxGradeSubtheme=" + this.maxGradeSubtheme + ", studentScore=" + this.studentScore + ")";
    }
}
