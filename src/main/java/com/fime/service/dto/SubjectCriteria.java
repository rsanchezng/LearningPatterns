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
 * Criteria class for the {@link com.fime.domain.Subject} entity. This class is used
 * in {@link com.fime.web.rest.SubjectResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /subjects?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SubjectCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter subjectName;

    private StringFilter subjectDescription;

    private IntegerFilter subjectCredits;

    private StringFilter subjectCreatedBy;

    private LocalDateFilter subjectCreationDate;

    private StringFilter subjectModifiedBy;

    private LocalDateFilter subjectModifiedDate;

    private LongFilter teacherId;

    public SubjectCriteria(){
    }

    public SubjectCriteria(SubjectCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.subjectName = other.subjectName == null ? null : other.subjectName.copy();
        this.subjectDescription = other.subjectDescription == null ? null : other.subjectDescription.copy();
        this.subjectCredits = other.subjectCredits == null ? null : other.subjectCredits.copy();
        this.subjectCreatedBy = other.subjectCreatedBy == null ? null : other.subjectCreatedBy.copy();
        this.subjectCreationDate = other.subjectCreationDate == null ? null : other.subjectCreationDate.copy();
        this.subjectModifiedBy = other.subjectModifiedBy == null ? null : other.subjectModifiedBy.copy();
        this.subjectModifiedDate = other.subjectModifiedDate == null ? null : other.subjectModifiedDate.copy();
        this.teacherId = other.teacherId == null ? null : other.teacherId.copy();
    }

    @Override
    public SubjectCriteria copy() {
        return new SubjectCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(StringFilter subjectName) {
        this.subjectName = subjectName;
    }

    public StringFilter getSubjectDescription() {
        return subjectDescription;
    }

    public void setSubjectDescription(StringFilter subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    public IntegerFilter getSubjectCredits() {
        return subjectCredits;
    }

    public void setSubjectCredits(IntegerFilter subjectCredits) {
        this.subjectCredits = subjectCredits;
    }

    public StringFilter getSubjectCreatedBy() {
        return subjectCreatedBy;
    }

    public void setSubjectCreatedBy(StringFilter subjectCreatedBy) {
        this.subjectCreatedBy = subjectCreatedBy;
    }

    public LocalDateFilter getSubjectCreationDate() {
        return subjectCreationDate;
    }

    public void setSubjectCreationDate(LocalDateFilter subjectCreationDate) {
        this.subjectCreationDate = subjectCreationDate;
    }

    public StringFilter getSubjectModifiedBy() {
        return subjectModifiedBy;
    }

    public void setSubjectModifiedBy(StringFilter subjectModifiedBy) {
        this.subjectModifiedBy = subjectModifiedBy;
    }

    public LocalDateFilter getSubjectModifiedDate() {
        return subjectModifiedDate;
    }

    public void setSubjectModifiedDate(LocalDateFilter subjectModifiedDate) {
        this.subjectModifiedDate = subjectModifiedDate;
    }

    public LongFilter getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(LongFilter teacherId) {
        this.teacherId = teacherId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SubjectCriteria that = (SubjectCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(subjectName, that.subjectName) &&
            Objects.equals(subjectDescription, that.subjectDescription) &&
            Objects.equals(subjectCredits, that.subjectCredits) &&
            Objects.equals(subjectCreatedBy, that.subjectCreatedBy) &&
            Objects.equals(subjectCreationDate, that.subjectCreationDate) &&
            Objects.equals(subjectModifiedBy, that.subjectModifiedBy) &&
            Objects.equals(subjectModifiedDate, that.subjectModifiedDate) &&
            Objects.equals(teacherId, that.teacherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        subjectName,
        subjectDescription,
        subjectCredits,
        subjectCreatedBy,
        subjectCreationDate,
        subjectModifiedBy,
        subjectModifiedDate,
        teacherId
        );
    }

    @Override
    public String toString() {
        return "SubjectCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (subjectName != null ? "subjectName=" + subjectName + ", " : "") +
                (subjectDescription != null ? "subjectDescription=" + subjectDescription + ", " : "") +
                (subjectCredits != null ? "subjectCredits=" + subjectCredits + ", " : "") +
                (subjectCreatedBy != null ? "subjectCreatedBy=" + subjectCreatedBy + ", " : "") +
                (subjectCreationDate != null ? "subjectCreationDate=" + subjectCreationDate + ", " : "") +
                (subjectModifiedBy != null ? "subjectModifiedBy=" + subjectModifiedBy + ", " : "") +
                (subjectModifiedDate != null ? "subjectModifiedDate=" + subjectModifiedDate + ", " : "") +
                (teacherId != null ? "teacherId=" + teacherId + ", " : "") +
            "}";
    }

}
