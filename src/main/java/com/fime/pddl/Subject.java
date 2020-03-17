package com.fime.pddl;

public class Subject {

    private String name;
    //	private List<Object> dependencies;
    private boolean done;
    private int credits;
    private int minGrade;

    public Subject() {
    }

    public Subject(String name, boolean done, int credits, int minGrade) {
		this.name = name;
		this.done = done;
		this.credits = credits;
		this.minGrade = minGrade;
	}

    public String getName() {
        return this.name;
    }

    public boolean isDone() {
        return this.done;
    }

    public int getCredits() {
        return this.credits;
    }

    public int getMinGrade() {
        return this.minGrade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setMinGrade(int minGrade) {
        this.minGrade = minGrade;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Subject)) return false;
        final Subject other = (Subject) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$name = this.name;
        final Object other$name = other.name;
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        if (this.done != other.done) return false;
        if (this.credits != other.credits) return false;
        if (this.minGrade != other.minGrade) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Subject;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.name;
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        result = result * PRIME + (this.done ? 79 : 97);
        result = result * PRIME + this.credits;
        result = result * PRIME + this.minGrade;
        return result;
    }

    public String toString() {
        return "Subject(name=" + this.name + ", done=" + this.done + ", credits=" + this.credits + ", minGrade=" + this.minGrade + ")";
    }
}
