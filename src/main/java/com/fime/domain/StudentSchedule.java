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

    @Column(name = "schedule_created_by")
    private String scheduleCreatedBy;

    @Column(name = "schedule_creation_date")
    private LocalDate scheduleCreationDate;

    @Column(name = "schedule_modified_by")
    private String scheduleModifiedBy;

    @Column(name = "schedule_modified_date")
    private LocalDate scheduleModifiedDate;

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

    public String getScheduleCreatedBy() {
        return scheduleCreatedBy;
    }

    public StudentSchedule scheduleCreatedBy(String scheduleCreatedBy) {
        this.scheduleCreatedBy = scheduleCreatedBy;
        return this;
    }

    public void setScheduleCreatedBy(String scheduleCreatedBy) {
        this.scheduleCreatedBy = scheduleCreatedBy;
    }

    public LocalDate getScheduleCreationDate() {
        return scheduleCreationDate;
    }

    public StudentSchedule scheduleCreationDate(LocalDate scheduleCreationDate) {
        this.scheduleCreationDate = scheduleCreationDate;
        return this;
    }

    public void setScheduleCreationDate(LocalDate scheduleCreationDate) {
        this.scheduleCreationDate = scheduleCreationDate;
    }

    public String getScheduleModifiedBy() {
        return scheduleModifiedBy;
    }

    public StudentSchedule scheduleModifiedBy(String scheduleModifiedBy) {
        this.scheduleModifiedBy = scheduleModifiedBy;
        return this;
    }

    public void setScheduleModifiedBy(String scheduleModifiedBy) {
        this.scheduleModifiedBy = scheduleModifiedBy;
    }

    public LocalDate getScheduleModifiedDate() {
        return scheduleModifiedDate;
    }

    public StudentSchedule scheduleModifiedDate(LocalDate scheduleModifiedDate) {
        this.scheduleModifiedDate = scheduleModifiedDate;
        return this;
    }

    public void setScheduleModifiedDate(LocalDate scheduleModifiedDate) {
        this.scheduleModifiedDate = scheduleModifiedDate;
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
            ", scheduleCreatedBy='" + getScheduleCreatedBy() + "'" +
            ", scheduleCreationDate='" + getScheduleCreationDate() + "'" +
            ", scheduleModifiedBy='" + getScheduleModifiedBy() + "'" +
            ", scheduleModifiedDate='" + getScheduleModifiedDate() + "'" +
            "}";
    }
}
