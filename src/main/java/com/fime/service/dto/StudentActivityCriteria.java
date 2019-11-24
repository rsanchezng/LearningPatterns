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
 * Criteria class for the {@link com.fime.domain.StudentActivity} entity. This class is used
 * in {@link com.fime.web.rest.StudentActivityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /student-activities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StudentActivityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter activityStartDate;

    private LocalDateFilter activityEndDate;

    private IntegerFilter activityGrade;

    private LocalDateFilter studentActivityGradeDate;

    private LocalDateFilter studentActivityCreatedDate;

    private StringFilter studentActivityCreatedBy;

    private LocalDateFilter studentActivityModifiedDate;

    private LocalDateFilter studentActivityModifiedBy;

    private LongFilter activityId;

    private LongFilter studentscheduleId;

    public StudentActivityCriteria(){
    }

    public StudentActivityCriteria(StudentActivityCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.activityStartDate = other.activityStartDate == null ? null : other.activityStartDate.copy();
        this.activityEndDate = other.activityEndDate == null ? null : other.activityEndDate.copy();
        this.activityGrade = other.activityGrade == null ? null : other.activityGrade.copy();
        this.studentActivityGradeDate = other.studentActivityGradeDate == null ? null : other.studentActivityGradeDate.copy();
        this.studentActivityCreatedDate = other.studentActivityCreatedDate == null ? null : other.studentActivityCreatedDate.copy();
        this.studentActivityCreatedBy = other.studentActivityCreatedBy == null ? null : other.studentActivityCreatedBy.copy();
        this.studentActivityModifiedDate = other.studentActivityModifiedDate == null ? null : other.studentActivityModifiedDate.copy();
        this.studentActivityModifiedBy = other.studentActivityModifiedBy == null ? null : other.studentActivityModifiedBy.copy();
        this.activityId = other.activityId == null ? null : other.activityId.copy();
        this.studentscheduleId = other.studentscheduleId == null ? null : other.studentscheduleId.copy();
    }

    @Override
    public StudentActivityCriteria copy() {
        return new StudentActivityCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getActivityStartDate() {
        return activityStartDate;
    }

    public void setActivityStartDate(LocalDateFilter activityStartDate) {
        this.activityStartDate = activityStartDate;
    }

    public LocalDateFilter getActivityEndDate() {
        return activityEndDate;
    }

    public void setActivityEndDate(LocalDateFilter activityEndDate) {
        this.activityEndDate = activityEndDate;
    }

    public IntegerFilter getActivityGrade() {
        return activityGrade;
    }

    public void setActivityGrade(IntegerFilter activityGrade) {
        this.activityGrade = activityGrade;
    }

    public LocalDateFilter getStudentActivityGradeDate() {
        return studentActivityGradeDate;
    }

    public void setStudentActivityGradeDate(LocalDateFilter studentActivityGradeDate) {
        this.studentActivityGradeDate = studentActivityGradeDate;
    }

    public LocalDateFilter getStudentActivityCreatedDate() {
        return studentActivityCreatedDate;
    }

    public void setStudentActivityCreatedDate(LocalDateFilter studentActivityCreatedDate) {
        this.studentActivityCreatedDate = studentActivityCreatedDate;
    }

    public StringFilter getStudentActivityCreatedBy() {
        return studentActivityCreatedBy;
    }

    public void setStudentActivityCreatedBy(StringFilter studentActivityCreatedBy) {
        this.studentActivityCreatedBy = studentActivityCreatedBy;
    }

    public LocalDateFilter getStudentActivityModifiedDate() {
        return studentActivityModifiedDate;
    }

    public void setStudentActivityModifiedDate(LocalDateFilter studentActivityModifiedDate) {
        this.studentActivityModifiedDate = studentActivityModifiedDate;
    }

    public LocalDateFilter getStudentActivityModifiedBy() {
        return studentActivityModifiedBy;
    }

    public void setStudentActivityModifiedBy(LocalDateFilter studentActivityModifiedBy) {
        this.studentActivityModifiedBy = studentActivityModifiedBy;
    }

    public LongFilter getActivityId() {
        return activityId;
    }

    public void setActivityId(LongFilter activityId) {
        this.activityId = activityId;
    }

    public LongFilter getStudentscheduleId() {
        return studentscheduleId;
    }

    public void setStudentscheduleId(LongFilter studentscheduleId) {
        this.studentscheduleId = studentscheduleId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StudentActivityCriteria that = (StudentActivityCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(activityStartDate, that.activityStartDate) &&
            Objects.equals(activityEndDate, that.activityEndDate) &&
            Objects.equals(activityGrade, that.activityGrade) &&
            Objects.equals(studentActivityGradeDate, that.studentActivityGradeDate) &&
            Objects.equals(studentActivityCreatedDate, that.studentActivityCreatedDate) &&
            Objects.equals(studentActivityCreatedBy, that.studentActivityCreatedBy) &&
            Objects.equals(studentActivityModifiedDate, that.studentActivityModifiedDate) &&
            Objects.equals(studentActivityModifiedBy, that.studentActivityModifiedBy) &&
            Objects.equals(activityId, that.activityId) &&
            Objects.equals(studentscheduleId, that.studentscheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        activityStartDate,
        activityEndDate,
        activityGrade,
        studentActivityGradeDate,
        studentActivityCreatedDate,
        studentActivityCreatedBy,
        studentActivityModifiedDate,
        studentActivityModifiedBy,
        activityId,
        studentscheduleId
        );
    }

    @Override
    public String toString() {
        return "StudentActivityCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (activityStartDate != null ? "activityStartDate=" + activityStartDate + ", " : "") +
                (activityEndDate != null ? "activityEndDate=" + activityEndDate + ", " : "") +
                (activityGrade != null ? "activityGrade=" + activityGrade + ", " : "") +
                (studentActivityGradeDate != null ? "studentActivityGradeDate=" + studentActivityGradeDate + ", " : "") +
                (studentActivityCreatedDate != null ? "studentActivityCreatedDate=" + studentActivityCreatedDate + ", " : "") +
                (studentActivityCreatedBy != null ? "studentActivityCreatedBy=" + studentActivityCreatedBy + ", " : "") +
                (studentActivityModifiedDate != null ? "studentActivityModifiedDate=" + studentActivityModifiedDate + ", " : "") +
                (studentActivityModifiedBy != null ? "studentActivityModifiedBy=" + studentActivityModifiedBy + ", " : "") +
                (activityId != null ? "activityId=" + activityId + ", " : "") +
                (studentscheduleId != null ? "studentscheduleId=" + studentscheduleId + ", " : "") +
            "}";
    }

}
