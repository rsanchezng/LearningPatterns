package com.fime.pddl;

public class Student {

    private String name;
    //	available-credits student1
    private int availableCredits;
    //	Esto hace referencia a total-credits-subject-gain student1
    private int acumulatedCredits;

    public Student() {
    }

    public String getName() {
        return this.name;
    }

    public int getAvailableCredits() {
        return this.availableCredits;
    }

    public int getAcumulatedCredits() {
        return this.acumulatedCredits;
    }

    public void setName(String name) {
        this.name = name;
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
        final Object this$name = this.name;
        final Object other$name = other.name;
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
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
        final Object $name = this.name;
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        result = result * PRIME + this.availableCredits;
        result = result * PRIME + this.acumulatedCredits;
        return result;
    }

    public String toString() {
        return "Student(name=" + this.name + ", availableCredits=" + this.availableCredits + ", acumulatedCredits=" + this.acumulatedCredits + ")";
    }
}
