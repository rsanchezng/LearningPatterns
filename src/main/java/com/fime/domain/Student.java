package com.fime.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "student_first_name")
    private String studentFirstName;

    @Column(name = "student_last_name")
    private String studentLastName;

    @Column(name = "student_email")
    private String studentEmail;

    @Column(name = "student_password")
    private String studentPassword;

    @Column(name = "student_credits")
    private String studentCredits;

    @Column(name = "student_created_by")
    private String studentCreatedBy;

    @Column(name = "student_creation_date")
    private LocalDate studentCreationDate;

    @Column(name = "student_modified_by")
    private String studentModifiedBy;

    @Column(name = "student_modified_date")
    private LocalDate studentModifiedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public Student studentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
        return this;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public Student studentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
        return this;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public Student studentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
        return this;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public Student studentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
        return this;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public String getStudentCredits() {
        return studentCredits;
    }

    public Student studentCredits(String studentCredits) {
        this.studentCredits = studentCredits;
        return this;
    }

    public void setStudentCredits(String studentCredits) {
        this.studentCredits = studentCredits;
    }

    public String getStudentCreatedBy() {
        return studentCreatedBy;
    }

    public Student studentCreatedBy(String studentCreatedBy) {
        this.studentCreatedBy = studentCreatedBy;
        return this;
    }

    public void setStudentCreatedBy(String studentCreatedBy) {
        this.studentCreatedBy = studentCreatedBy;
    }

    public LocalDate getStudentCreationDate() {
        return studentCreationDate;
    }

    public Student studentCreationDate(LocalDate studentCreationDate) {
        this.studentCreationDate = studentCreationDate;
        return this;
    }

    public void setStudentCreationDate(LocalDate studentCreationDate) {
        this.studentCreationDate = studentCreationDate;
    }

    public String getStudentModifiedBy() {
        return studentModifiedBy;
    }

    public Student studentModifiedBy(String studentModifiedBy) {
        this.studentModifiedBy = studentModifiedBy;
        return this;
    }

    public void setStudentModifiedBy(String studentModifiedBy) {
        this.studentModifiedBy = studentModifiedBy;
    }

    public LocalDate getStudentModifiedDate() {
        return studentModifiedDate;
    }

    public Student studentModifiedDate(LocalDate studentModifiedDate) {
        this.studentModifiedDate = studentModifiedDate;
        return this;
    }

    public void setStudentModifiedDate(LocalDate studentModifiedDate) {
        this.studentModifiedDate = studentModifiedDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", studentFirstName='" + getStudentFirstName() + "'" +
            ", studentLastName='" + getStudentLastName() + "'" +
            ", studentEmail='" + getStudentEmail() + "'" +
            ", studentPassword='" + getStudentPassword() + "'" +
            ", studentCredits='" + getStudentCredits() + "'" +
            ", studentCreatedBy='" + getStudentCreatedBy() + "'" +
            ", studentCreationDate='" + getStudentCreationDate() + "'" +
            ", studentModifiedBy='" + getStudentModifiedBy() + "'" +
            ", studentModifiedDate='" + getStudentModifiedDate() + "'" +
            "}";
    }
}
