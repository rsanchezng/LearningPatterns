package com.fime.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.fime.domain.Student} entity. This class is used
 * in {@link com.fime.web.rest.StudentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /students?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StudentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter studentFirstName;

    private StringFilter studentLastName;

    private StringFilter studentEmail;

    private StringFilter studentPassword;

    private StringFilter studentCredits;

    private StringFilter studentCreatedBy;

    private LocalDateFilter studentCreationDate;

    private StringFilter studentModifiedBy;

    private LocalDateFilter studentModifiedDate;

    public StudentCriteria(){
    }

    public StudentCriteria(StudentCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.studentFirstName = other.studentFirstName == null ? null : other.studentFirstName.copy();
        this.studentLastName = other.studentLastName == null ? null : other.studentLastName.copy();
        this.studentEmail = other.studentEmail == null ? null : other.studentEmail.copy();
        this.studentPassword = other.studentPassword == null ? null : other.studentPassword.copy();
        this.studentCredits = other.studentCredits == null ? null : other.studentCredits.copy();
        this.studentCreatedBy = other.studentCreatedBy == null ? null : other.studentCreatedBy.copy();
        this.studentCreationDate = other.studentCreationDate == null ? null : other.studentCreationDate.copy();
        this.studentModifiedBy = other.studentModifiedBy == null ? null : other.studentModifiedBy.copy();
        this.studentModifiedDate = other.studentModifiedDate == null ? null : other.studentModifiedDate.copy();
    }

    @Override
    public StudentCriteria copy() {
        return new StudentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(StringFilter studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public StringFilter getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(StringFilter studentLastName) {
        this.studentLastName = studentLastName;
    }

    public StringFilter getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(StringFilter studentEmail) {
        this.studentEmail = studentEmail;
    }

    public StringFilter getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(StringFilter studentPassword) {
        this.studentPassword = studentPassword;
    }

    public StringFilter getStudentCredits() {
        return studentCredits;
    }

    public void setStudentCredits(StringFilter studentCredits) {
        this.studentCredits = studentCredits;
    }

    public StringFilter getStudentCreatedBy() {
        return studentCreatedBy;
    }

    public void setStudentCreatedBy(StringFilter studentCreatedBy) {
        this.studentCreatedBy = studentCreatedBy;
    }

    public LocalDateFilter getStudentCreationDate() {
        return studentCreationDate;
    }

    public void setStudentCreationDate(LocalDateFilter studentCreationDate) {
        this.studentCreationDate = studentCreationDate;
    }

    public StringFilter getStudentModifiedBy() {
        return studentModifiedBy;
    }

    public void setStudentModifiedBy(StringFilter studentModifiedBy) {
        this.studentModifiedBy = studentModifiedBy;
    }

    public LocalDateFilter getStudentModifiedDate() {
        return studentModifiedDate;
    }

    public void setStudentModifiedDate(LocalDateFilter studentModifiedDate) {
        this.studentModifiedDate = studentModifiedDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StudentCriteria that = (StudentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(studentFirstName, that.studentFirstName) &&
            Objects.equals(studentLastName, that.studentLastName) &&
            Objects.equals(studentEmail, that.studentEmail) &&
            Objects.equals(studentPassword, that.studentPassword) &&
            Objects.equals(studentCredits, that.studentCredits) &&
            Objects.equals(studentCreatedBy, that.studentCreatedBy) &&
            Objects.equals(studentCreationDate, that.studentCreationDate) &&
            Objects.equals(studentModifiedBy, that.studentModifiedBy) &&
            Objects.equals(studentModifiedDate, that.studentModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        studentFirstName,
        studentLastName,
        studentEmail,
        studentPassword,
        studentCredits,
        studentCreatedBy,
        studentCreationDate,
        studentModifiedBy,
        studentModifiedDate
        );
    }

    @Override
    public String toString() {
        return "StudentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (studentFirstName != null ? "studentFirstName=" + studentFirstName + ", " : "") +
                (studentLastName != null ? "studentLastName=" + studentLastName + ", " : "") +
                (studentEmail != null ? "studentEmail=" + studentEmail + ", " : "") +
                (studentPassword != null ? "studentPassword=" + studentPassword + ", " : "") +
                (studentCredits != null ? "studentCredits=" + studentCredits + ", " : "") +
                (studentCreatedBy != null ? "studentCreatedBy=" + studentCreatedBy + ", " : "") +
                (studentCreationDate != null ? "studentCreationDate=" + studentCreationDate + ", " : "") +
                (studentModifiedBy != null ? "studentModifiedBy=" + studentModifiedBy + ", " : "") +
                (studentModifiedDate != null ? "studentModifiedDate=" + studentModifiedDate + ", " : "") +
            "}";
    }

}
