package com.fime.pddl;

import java.util.List;

public class Activity {

    private String identifier;
    private String name;
    private Subtheme parentSubtheme;
    //	si dependencies es nula o vacia califica para: (not-has-reqs LA1050) y si esta llena puede ser: (has-reqs A4 Binary)
    private List<Object> dependencies;
    private boolean done;
    private int value; //Maestro
    private int studentGrade;
    private int duration;
    private List<Resource> resources;

    public Activity() {
    }

    public Activity(String identifier, String name, Subtheme parentSubtheme, List<Object> dependencies,
                    boolean done, int value, int studentGrade, int duration, List<Resource> resources) {
		this.identifier = identifier;
		this.name = name;
		this.parentSubtheme = parentSubtheme;
		this.dependencies = dependencies;
		this.done = done;
		this.value = value;
		this.studentGrade = studentGrade;
		this.duration = duration;
		this.resources = resources;
	}

	public String getIdentifier() {
        return this.identifier;
    }

    public String getName() {
        return this.name;
    }

    public Subtheme getParentSubtheme() {
        return this.parentSubtheme;
    }

    public List<Object> getDependencies() {
        return this.dependencies;
    }

    public boolean isDone() {
        return this.done;
    }

    public int getValue() {
        return this.value;
    }

    public int getStudentGrade() {
        return this.studentGrade;
    }

    public int getDuration() {
        return this.duration;
    }

    public List<Resource> getResources() {
        return this.resources;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentSubtheme(Subtheme parentSubtheme) {
        this.parentSubtheme = parentSubtheme;
    }

    public void setDependencies(List<Object> dependencies) {
        this.dependencies = dependencies;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setStudentGrade(int studentGrade) {
        this.studentGrade = studentGrade;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Activity)) return false;
        final Activity other = (Activity) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$activityIdentifier = this.identifier;
        final Object other$activityIdentifier = other.identifier;
        if (this$activityIdentifier == null ? other$activityIdentifier != null : !this$activityIdentifier.equals(other$activityIdentifier))
            return false;
        final Object this$activityName = this.name;
        final Object other$activityName = other.name;
        if (this$activityName == null ? other$activityName != null : !this$activityName.equals(other$activityName))
            return false;
        final Object this$parentSubtheme = this.parentSubtheme;
        final Object other$parentSubtheme = other.parentSubtheme;
        if (this$parentSubtheme == null ? other$parentSubtheme != null : !this$parentSubtheme.equals(other$parentSubtheme))
            return false;
        final Object this$dependencies = this.dependencies;
        final Object other$dependencies = other.dependencies;
        if (this$dependencies == null ? other$dependencies != null : !this$dependencies.equals(other$dependencies))
            return false;
        if (this.done != other.done) return false;
        if (this.value != other.value) return false;
        if (this.studentGrade != other.studentGrade) return false;
        if (this.duration != other.duration) return false;
        final Object this$resources = this.resources;
        final Object other$resources = other.resources;
        if (this$resources == null ? other$resources != null : !this$resources.equals(other$resources)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Activity;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $activityIdentifier = this.identifier;
        result = result * PRIME + ($activityIdentifier == null ? 43 : $activityIdentifier.hashCode());
        final Object $activityName = this.name;
        result = result * PRIME + ($activityName == null ? 43 : $activityName.hashCode());
        final Object $parentSubtheme = this.parentSubtheme;
        result = result * PRIME + ($parentSubtheme == null ? 43 : $parentSubtheme.hashCode());
        final Object $dependencies = this.dependencies;
        result = result * PRIME + ($dependencies == null ? 43 : $dependencies.hashCode());
        result = result * PRIME + (this.done ? 79 : 97);
        result = result * PRIME + this.value;
        result = result * PRIME + this.studentGrade;
        result = result * PRIME + this.duration;
        final Object $resources = this.resources;
        result = result * PRIME + ($resources == null ? 43 : $resources.hashCode());
        return result;
    }

    public String toString() {
        return "Activity(activityIdentifier=" + this.identifier + ", activityName=" + this.name + ", parentSubtheme=" + this.parentSubtheme + ", dependencies=" + this.dependencies + ", activityDone=" + this.done + ", value=" + this.value + ", studentGrade=" + this.studentGrade + ", duration=" + this.duration + ", resources=" + this.resources + ")";
    }
}
