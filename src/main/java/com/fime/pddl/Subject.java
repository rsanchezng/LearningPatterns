package com.fime.pddl;

public class Subject {

    private String subjectName;
    //	private List<Object> dependencies;
    private boolean subjectDone;
    private int credits;
    private int minGrade;
    
    public Subject() {
    }
    
    public Subject(String subjectName, boolean subjectDone, int credits, int minGrade) {
		this.subjectName = subjectName;
		this.subjectDone = subjectDone;
		this.credits = credits;
		this.minGrade = minGrade;
	}

	public String getSubjectName() {
        return this.subjectName;
    }

    public boolean isSubjectDone() {
        return this.subjectDone;
    }

    public int getCredits() {
        return this.credits;
    }

    public int getMinGrade() {
        return this.minGrade;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setSubjectDone(boolean subjectDone) {
        this.subjectDone = subjectDone;
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
        final Object this$subjectName = this.subjectName;
        final Object other$subjectName = other.subjectName;
        if (this$subjectName == null ? other$subjectName != null : !this$subjectName.equals(other$subjectName))
            return false;
        if (this.subjectDone != other.subjectDone) return false;
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
        final Object $subjectName = this.subjectName;
        result = result * PRIME + ($subjectName == null ? 43 : $subjectName.hashCode());
        result = result * PRIME + (this.subjectDone ? 79 : 97);
        result = result * PRIME + this.credits;
        result = result * PRIME + this.minGrade;
        return result;
    }

    public String toString() {
        return "Subject(subjectName=" + this.subjectName + ", subjectDone=" + this.subjectDone + ", credits=" + this.credits + ", minGrade=" + this.minGrade + ")";
    }
}
