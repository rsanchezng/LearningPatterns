package com.fime.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A StudentSchedule.
 */
@Entity
@Table(name = "student_schedule")
public class StudentSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "student_schedule_created_by")
    private String studentScheduleCreatedBy;

    @Column(name = "student_schedule_creation_date")
    private LocalDate studentScheduleCreationDate;

    @Column(name = "student_schedule_modified_by")
    private String studentScheduleModifiedBy;

    @Column(name = "student_schedule_modified_date")
    private LocalDate studentScheduleModifiedDate;

    @ManyToOne
    @JsonIgnoreProperties("studentSchedules")
    private Group group;

    @ManyToOne
    @JsonIgnoreProperties("studentSchedules")
    private Student student;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentScheduleCreatedBy() {
        return studentScheduleCreatedBy;
    }

    public StudentSchedule studentScheduleCreatedBy(String studentScheduleCreatedBy) {
        this.studentScheduleCreatedBy = studentScheduleCreatedBy;
        return this;
    }

    public void setStudentScheduleCreatedBy(String studentScheduleCreatedBy) {
        this.studentScheduleCreatedBy = studentScheduleCreatedBy;
    }

    public LocalDate getStudentScheduleCreationDate() {
        return studentScheduleCreationDate;
    }

    public StudentSchedule studentScheduleCreationDate(LocalDate studentScheduleCreationDate) {
        this.studentScheduleCreationDate = studentScheduleCreationDate;
        return this;
    }

    public void setStudentScheduleCreationDate(LocalDate studentScheduleCreationDate) {
        this.studentScheduleCreationDate = studentScheduleCreationDate;
    }

    public String getStudentScheduleModifiedBy() {
        return studentScheduleModifiedBy;
    }

    public StudentSchedule studentScheduleModifiedBy(String studentScheduleModifiedBy) {
        this.studentScheduleModifiedBy = studentScheduleModifiedBy;
        return this;
    }

    public void setStudentScheduleModifiedBy(String studentScheduleModifiedBy) {
        this.studentScheduleModifiedBy = studentScheduleModifiedBy;
    }

    public LocalDate getStudentScheduleModifiedDate() {
        return studentScheduleModifiedDate;
    }

    public StudentSchedule studentScheduleModifiedDate(LocalDate studentScheduleModifiedDate) {
        this.studentScheduleModifiedDate = studentScheduleModifiedDate;
        return this;
    }

    public void setStudentScheduleModifiedDate(LocalDate studentScheduleModifiedDate) {
        this.studentScheduleModifiedDate = studentScheduleModifiedDate;
    }

    public Group getGroup() {
        return group;
    }

    public StudentSchedule group(Group group) {
        this.group = group;
        return this;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Student getStudent() {
        return student;
    }

    public StudentSchedule student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentSchedule)) {
            return false;
        }
        return id != null && id.equals(((StudentSchedule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StudentSchedule{" +
            "id=" + getId() +
            ", studentScheduleCreatedBy='" + getStudentScheduleCreatedBy() + "'" +
            ", studentScheduleCreationDate='" + getStudentScheduleCreationDate() + "'" +
            ", studentScheduleModifiedBy='" + getStudentScheduleModifiedBy() + "'" +
            ", studentScheduleModifiedDate='" + getStudentScheduleModifiedDate() + "'" +
            "}";
    }
}
