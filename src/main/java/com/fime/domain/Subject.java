package com.fime.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Subject.
 */
@Entity
@Table(name = "subject")
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "subject_description")
    private String subjectDescription;

    @Column(name = "subject_credits")
    private Integer subjectCredits;

    @Column(name = "subject_created_by")
    private String subjectCreatedBy;

    @Column(name = "subject_creation_date")
    private LocalDate subjectCreationDate;

    @Column(name = "subject_modified_by")
    private String subjectModifiedBy;

    @Column(name = "subject_modified_date")
    private LocalDate subjectModifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("subjects")
    private Teacher teacher;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Subject subjectName(String subjectName) {
        this.subjectName = subjectName;
        return this;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public Subject subjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
        return this;
    }

    public void setSubjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    public Integer getSubjectCredits() {
        return subjectCredits;
    }

    public Subject subjectCredits(Integer subjectCredits) {
        this.subjectCredits = subjectCredits;
        return this;
    }

    public void setSubjectCredits(Integer subjectCredits) {
        this.subjectCredits = subjectCredits;
    }

    public String getSubjectCreatedBy() {
        return subjectCreatedBy;
    }

    public Subject subjectCreatedBy(String subjectCreatedBy) {
        this.subjectCreatedBy = subjectCreatedBy;
        return this;
    }

    public void setSubjectCreatedBy(String subjectCreatedBy) {
        this.subjectCreatedBy = subjectCreatedBy;
    }

    public LocalDate getSubjectCreationDate() {
        return subjectCreationDate;
    }

    public Subject subjectCreationDate(LocalDate subjectCreationDate) {
        this.subjectCreationDate = subjectCreationDate;
        return this;
    }

    public void setSubjectCreationDate(LocalDate subjectCreationDate) {
        this.subjectCreationDate = subjectCreationDate;
    }

    public String getSubjectModifiedBy() {
        return subjectModifiedBy;
    }

    public Subject subjectModifiedBy(String subjectModifiedBy) {
        this.subjectModifiedBy = subjectModifiedBy;
        return this;
    }

    public void setSubjectModifiedBy(String subjectModifiedBy) {
        this.subjectModifiedBy = subjectModifiedBy;
    }

    public LocalDate getSubjectModifiedDate() {
        return subjectModifiedDate;
    }

    public Subject subjectModifiedDate(LocalDate subjectModifiedDate) {
        this.subjectModifiedDate = subjectModifiedDate;
        return this;
    }

    public void setSubjectModifiedDate(LocalDate subjectModifiedDate) {
        this.subjectModifiedDate = subjectModifiedDate;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Subject teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subject)) {
            return false;
        }
        return id != null && id.equals(((Subject) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Subject{" +
            "id=" + getId() +
            ", subjectName='" + getSubjectName() + "'" +
            ", subjectDescription='" + getSubjectDescription() + "'" +
            ", subjectCredits=" + getSubjectCredits() +
            ", subjectCreatedBy='" + getSubjectCreatedBy() + "'" +
            ", subjectCreationDate='" + getSubjectCreationDate() + "'" +
            ", subjectModifiedBy='" + getSubjectModifiedBy() + "'" +
            ", subjectModifiedDate='" + getSubjectModifiedDate() + "'" +
            "}";
    }
}
