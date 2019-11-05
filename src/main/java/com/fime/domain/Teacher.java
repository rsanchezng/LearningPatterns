package com.fime.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Teacher.
 */
@Entity
@Table(name = "teacher")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "teacher_first_name")
    private String teacherFirstName;

    @Column(name = "teacher_last_name")
    private String teacherLastName;

    @Column(name = "teacher_email")
    private String teacherEmail;

    @Column(name = "teacher_password")
    private String teacherPassword;

    @Column(name = "teacher_created_by")
    private String teacherCreatedBy;

    @Column(name = "teacher_creation_date")
    private LocalDate teacherCreationDate;

    @Column(name = "teacher_modified_by")
    private String teacherModifiedBy;

    @Column(name = "teacher_modified_date")
    private LocalDate teacherModifiedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeacherFirstName() {
        return teacherFirstName;
    }

    public Teacher teacherFirstName(String teacherFirstName) {
        this.teacherFirstName = teacherFirstName;
        return this;
    }

    public void setTeacherFirstName(String teacherFirstName) {
        this.teacherFirstName = teacherFirstName;
    }

    public String getTeacherLastName() {
        return teacherLastName;
    }

    public Teacher teacherLastName(String teacherLastName) {
        this.teacherLastName = teacherLastName;
        return this;
    }

    public void setTeacherLastName(String teacherLastName) {
        this.teacherLastName = teacherLastName;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public Teacher teacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
        return this;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getTeacherPassword() {
        return teacherPassword;
    }

    public Teacher teacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
        return this;
    }

    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }

    public String getTeacherCreatedBy() {
        return teacherCreatedBy;
    }

    public Teacher teacherCreatedBy(String teacherCreatedBy) {
        this.teacherCreatedBy = teacherCreatedBy;
        return this;
    }

    public void setTeacherCreatedBy(String teacherCreatedBy) {
        this.teacherCreatedBy = teacherCreatedBy;
    }

    public LocalDate getTeacherCreationDate() {
        return teacherCreationDate;
    }

    public Teacher teacherCreationDate(LocalDate teacherCreationDate) {
        this.teacherCreationDate = teacherCreationDate;
        return this;
    }

    public void setTeacherCreationDate(LocalDate teacherCreationDate) {
        this.teacherCreationDate = teacherCreationDate;
    }

    public String getTeacherModifiedBy() {
        return teacherModifiedBy;
    }

    public Teacher teacherModifiedBy(String teacherModifiedBy) {
        this.teacherModifiedBy = teacherModifiedBy;
        return this;
    }

    public void setTeacherModifiedBy(String teacherModifiedBy) {
        this.teacherModifiedBy = teacherModifiedBy;
    }

    public LocalDate getTeacherModifiedDate() {
        return teacherModifiedDate;
    }

    public Teacher teacherModifiedDate(LocalDate teacherModifiedDate) {
        this.teacherModifiedDate = teacherModifiedDate;
        return this;
    }

    public void setTeacherModifiedDate(LocalDate teacherModifiedDate) {
        this.teacherModifiedDate = teacherModifiedDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Teacher)) {
            return false;
        }
        return id != null && id.equals(((Teacher) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Teacher{" +
            "id=" + getId() +
            ", teacherFirstName='" + getTeacherFirstName() + "'" +
            ", teacherLastName='" + getTeacherLastName() + "'" +
            ", teacherEmail='" + getTeacherEmail() + "'" +
            ", teacherPassword='" + getTeacherPassword() + "'" +
            ", teacherCreatedBy='" + getTeacherCreatedBy() + "'" +
            ", teacherCreationDate='" + getTeacherCreationDate() + "'" +
            ", teacherModifiedBy='" + getTeacherModifiedBy() + "'" +
            ", teacherModifiedDate='" + getTeacherModifiedDate() + "'" +
            "}";
    }
}
