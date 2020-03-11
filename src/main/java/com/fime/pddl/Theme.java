package com.fime.pddl;

public class Theme {

    private String themeName;
    private Subject parentSubject;
    //	private List<Object> dependencies;
    private boolean themeDone;

    public Theme() {
    }


    public String getThemeName() {
        return this.themeName;
    }

    public Subject getParentSubject() {
        return this.parentSubject;
    }

    public boolean isThemeDone() {
        return this.themeDone;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public void setParentSubject(Subject parentSubject) {
        this.parentSubject = parentSubject;
    }

    public void setThemeDone(boolean themeDone) {
        this.themeDone = themeDone;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Theme)) return false;
        final Theme other = (Theme) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$themeName = this.themeName;
        final Object other$themeName = other.themeName;
        if (this$themeName == null ? other$themeName != null : !this$themeName.equals(other$themeName)) return false;
        final Object this$parentSubject = this.parentSubject;
        final Object other$parentSubject = other.parentSubject;
        if (this$parentSubject == null ? other$parentSubject != null : !this$parentSubject.equals(other$parentSubject))
            return false;
        if (this.themeDone != other.themeDone) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Theme;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $themeName = this.themeName;
        result = result * PRIME + ($themeName == null ? 43 : $themeName.hashCode());
        final Object $parentSubject = this.parentSubject;
        result = result * PRIME + ($parentSubject == null ? 43 : $parentSubject.hashCode());
        result = result * PRIME + (this.themeDone ? 79 : 97);
        return result;
    }

    public String toString() {
        return "Theme(themeName=" + this.themeName + ", parentSubject=" + this.parentSubject + ", themeDone=" + this.themeDone + ")";
    }
}
