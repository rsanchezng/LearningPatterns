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
 * Criteria class for the {@link com.fime.domain.Teacher} entity. This class is used
 * in {@link com.fime.web.rest.TeacherResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /teachers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TeacherCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter teacherFirstName;

    private StringFilter teacherLastName;

    private StringFilter teacherEmail;

    private StringFilter teacherPassword;

    private StringFilter teacherCreatedBy;

    private LocalDateFilter teacherCreationDate;

    private StringFilter teacherModifiedBy;

    private LocalDateFilter teacherModifiedDate;

    public TeacherCriteria(){
    }

    public TeacherCriteria(TeacherCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.teacherFirstName = other.teacherFirstName == null ? null : other.teacherFirstName.copy();
        this.teacherLastName = other.teacherLastName == null ? null : other.teacherLastName.copy();
        this.teacherEmail = other.teacherEmail == null ? null : other.teacherEmail.copy();
        this.teacherPassword = other.teacherPassword == null ? null : other.teacherPassword.copy();
        this.teacherCreatedBy = other.teacherCreatedBy == null ? null : other.teacherCreatedBy.copy();
        this.teacherCreationDate = other.teacherCreationDate == null ? null : other.teacherCreationDate.copy();
        this.teacherModifiedBy = other.teacherModifiedBy == null ? null : other.teacherModifiedBy.copy();
        this.teacherModifiedDate = other.teacherModifiedDate == null ? null : other.teacherModifiedDate.copy();
    }

    @Override
    public TeacherCriteria copy() {
        return new TeacherCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTeacherFirstName() {
        return teacherFirstName;
    }

    public void setTeacherFirstName(StringFilter teacherFirstName) {
        this.teacherFirstName = teacherFirstName;
    }

    public StringFilter getTeacherLastName() {
        return teacherLastName;
    }

    public void setTeacherLastName(StringFilter teacherLastName) {
        this.teacherLastName = teacherLastName;
    }

    public StringFilter getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(StringFilter teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public StringFilter getTeacherPassword() {
        return teacherPassword;
    }

    public void setTeacherPassword(StringFilter teacherPassword) {
        this.teacherPassword = teacherPassword;
    }

    public StringFilter getTeacherCreatedBy() {
        return teacherCreatedBy;
    }

    public void setTeacherCreatedBy(StringFilter teacherCreatedBy) {
        this.teacherCreatedBy = teacherCreatedBy;
    }

    public LocalDateFilter getTeacherCreationDate() {
        return teacherCreationDate;
    }

    public void setTeacherCreationDate(LocalDateFilter teacherCreationDate) {
        this.teacherCreationDate = teacherCreationDate;
    }

    public StringFilter getTeacherModifiedBy() {
        return teacherModifiedBy;
    }

    public void setTeacherModifiedBy(StringFilter teacherModifiedBy) {
        this.teacherModifiedBy = teacherModifiedBy;
    }

    public LocalDateFilter getTeacherModifiedDate() {
        return teacherModifiedDate;
    }

    public void setTeacherModifiedDate(LocalDateFilter teacherModifiedDate) {
        this.teacherModifiedDate = teacherModifiedDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TeacherCriteria that = (TeacherCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(teacherFirstName, that.teacherFirstName) &&
            Objects.equals(teacherLastName, that.teacherLastName) &&
            Objects.equals(teacherEmail, that.teacherEmail) &&
            Objects.equals(teacherPassword, that.teacherPassword) &&
            Objects.equals(teacherCreatedBy, that.teacherCreatedBy) &&
            Objects.equals(teacherCreationDate, that.teacherCreationDate) &&
            Objects.equals(teacherModifiedBy, that.teacherModifiedBy) &&
            Objects.equals(teacherModifiedDate, that.teacherModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        teacherFirstName,
        teacherLastName,
        teacherEmail,
        teacherPassword,
        teacherCreatedBy,
        teacherCreationDate,
        teacherModifiedBy,
        teacherModifiedDate
        );
    }

    @Override
    public String toString() {
        return "TeacherCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (teacherFirstName != null ? "teacherFirstName=" + teacherFirstName + ", " : "") +
                (teacherLastName != null ? "teacherLastName=" + teacherLastName + ", " : "") +
                (teacherEmail != null ? "teacherEmail=" + teacherEmail + ", " : "") +
                (teacherPassword != null ? "teacherPassword=" + teacherPassword + ", " : "") +
                (teacherCreatedBy != null ? "teacherCreatedBy=" + teacherCreatedBy + ", " : "") +
                (teacherCreationDate != null ? "teacherCreationDate=" + teacherCreationDate + ", " : "") +
                (teacherModifiedBy != null ? "teacherModifiedBy=" + teacherModifiedBy + ", " : "") +
                (teacherModifiedDate != null ? "teacherModifiedDate=" + teacherModifiedDate + ", " : "") +
            "}";
    }

}
