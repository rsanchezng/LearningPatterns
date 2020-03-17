package com.fime.pddl;

public class Theme {

    private String name;
    private Subject parentSubject;
    //	private List<Object> dependencies;
    private boolean done;

    public Theme() {
    }

    public Theme(String name, Subject parentSubject, boolean done) {
		this.name = name;
		this.parentSubject = parentSubject;
		this.done = done;
	}

    public String getName() {
        return this.name;
    }

    public Subject getParentSubject() {
        return this.parentSubject;
    }

    public boolean isDone() {
        return this.done;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentSubject(Subject parentSubject) {
        this.parentSubject = parentSubject;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Theme)) return false;
        final Theme other = (Theme) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$name = this.name;
        final Object other$name = other.name;
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$parentSubject = this.parentSubject;
        final Object other$parentSubject = other.parentSubject;
        if (this$parentSubject == null ? other$parentSubject != null : !this$parentSubject.equals(other$parentSubject))
            return false;
        if (this.done != other.done) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Theme;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.name;
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $parentSubject = this.parentSubject;
        result = result * PRIME + ($parentSubject == null ? 43 : $parentSubject.hashCode());
        result = result * PRIME + (this.done ? 79 : 97);
        return result;
    }

    public String toString() {
        return "Theme(name=" + this.name + ", parentSubject=" + this.parentSubject + ", done=" + this.done + ")";
    }
}
