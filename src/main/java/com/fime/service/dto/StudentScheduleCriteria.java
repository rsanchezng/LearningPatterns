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
 * Criteria class for the {@link com.fime.domain.StudentSchedule} entity. This class is used
 * in {@link com.fime.web.rest.StudentScheduleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /student-schedules?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StudentScheduleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter studentScheduleCreatedBy;

    private LocalDateFilter studentScheduleCreationDate;

    private StringFilter studentScheduleModifiedBy;

    private LocalDateFilter studentScheduleModifiedDate;

    private LongFilter groupId;

    private LongFilter studentId;

    public StudentScheduleCriteria(){
    }

    public StudentScheduleCriteria(StudentScheduleCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.studentScheduleCreatedBy = other.studentScheduleCreatedBy == null ? null : other.studentScheduleCreatedBy.copy();
        this.studentScheduleCreationDate = other.studentScheduleCreationDate == null ? null : other.studentScheduleCreationDate.copy();
        this.studentScheduleModifiedBy = other.studentScheduleModifiedBy == null ? null : other.studentScheduleModifiedBy.copy();
        this.studentScheduleModifiedDate = other.studentScheduleModifiedDate == null ? null : other.studentScheduleModifiedDate.copy();
        this.groupId = other.groupId == null ? null : other.groupId.copy();
        this.studentId = other.studentId == null ? null : other.studentId.copy();
    }

    @Override
    public StudentScheduleCriteria copy() {
        return new StudentScheduleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStudentScheduleCreatedBy() {
        return studentScheduleCreatedBy;
    }

    public void setStudentScheduleCreatedBy(StringFilter studentScheduleCreatedBy) {
        this.studentScheduleCreatedBy = studentScheduleCreatedBy;
    }

    public LocalDateFilter getStudentScheduleCreationDate() {
        return studentScheduleCreationDate;
    }

    public void setStudentScheduleCreationDate(LocalDateFilter studentScheduleCreationDate) {
        this.studentScheduleCreationDate = studentScheduleCreationDate;
    }

    public StringFilter getStudentScheduleModifiedBy() {
        return studentScheduleModifiedBy;
    }

    public void setStudentScheduleModifiedBy(StringFilter studentScheduleModifiedBy) {
        this.studentScheduleModifiedBy = studentScheduleModifiedBy;
    }

    public LocalDateFilter getStudentScheduleModifiedDate() {
        return studentScheduleModifiedDate;
    }

    public void setStudentScheduleModifiedDate(LocalDateFilter studentScheduleModifiedDate) {
        this.studentScheduleModifiedDate = studentScheduleModifiedDate;
    }

    public LongFilter getGroupId() {
        return groupId;
    }

    public void setGroupId(LongFilter groupId) {
        this.groupId = groupId;
    }

    public LongFilter getStudentId() {
        return studentId;
    }

    public void setStudentId(LongFilter studentId) {
        this.studentId = studentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StudentScheduleCriteria that = (StudentScheduleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(studentScheduleCreatedBy, that.studentScheduleCreatedBy) &&
            Objects.equals(studentScheduleCreationDate, that.studentScheduleCreationDate) &&
            Objects.equals(studentScheduleModifiedBy, that.studentScheduleModifiedBy) &&
            Objects.equals(studentScheduleModifiedDate, that.studentScheduleModifiedDate) &&
            Objects.equals(groupId, that.groupId) &&
            Objects.equals(studentId, that.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        studentScheduleCreatedBy,
        studentScheduleCreationDate,
        studentScheduleModifiedBy,
        studentScheduleModifiedDate,
        groupId,
        studentId
        );
    }

    @Override
    public String toString() {
        return "StudentScheduleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (studentScheduleCreatedBy != null ? "studentScheduleCreatedBy=" + studentScheduleCreatedBy + ", " : "") +
                (studentScheduleCreationDate != null ? "studentScheduleCreationDate=" + studentScheduleCreationDate + ", " : "") +
                (studentScheduleModifiedBy != null ? "studentScheduleModifiedBy=" + studentScheduleModifiedBy + ", " : "") +
                (studentScheduleModifiedDate != null ? "studentScheduleModifiedDate=" + studentScheduleModifiedDate + ", " : "") +
                (groupId != null ? "groupId=" + groupId + ", " : "") +
                (studentId != null ? "studentId=" + studentId + ", " : "") +
            "}";
    }

}
