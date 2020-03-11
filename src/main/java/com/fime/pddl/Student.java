package com.fime.pddl;

public class Student {

    private String studentName;
    //	available-credits student1
    private int availableCredits;
    //	Esto hace referencia a total-credits-subject-gain student1
    private int acumulatedCredits;

    public Student() {
    }

    public String getStudentName() {
        return this.studentName;
    }

    public int getAvailableCredits() {
        return this.availableCredits;
    }

    public int getAcumulatedCredits() {
        return this.acumulatedCredits;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setAvailableCredits(int availableCredits) {
        this.availableCredits = availableCredits;
    }

    public void setAcumulatedCredits(int acumulatedCredits) {
        this.acumulatedCredits = acumulatedCredits;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Student)) return false;
        final Student other = (Student) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$studentName = this.studentName;
        final Object other$studentName = other.studentName;
        if (this$studentName == null ? other$studentName != null : !this$studentName.equals(other$studentName))
            return false;
        if (this.availableCredits != other.availableCredits) return false;
        if (this.acumulatedCredits != other.acumulatedCredits) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Student;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $studentName = this.studentName;
        result = result * PRIME + ($studentName == null ? 43 : $studentName.hashCode());
        result = result * PRIME + this.availableCredits;
        result = result * PRIME + this.acumulatedCredits;
        return result;
    }

    public String toString() {
        return "Student(studentName=" + this.studentName + ", availableCredits=" + this.availableCredits + ", acumulatedCredits=" + this.acumulatedCredits + ")";
    }
}
