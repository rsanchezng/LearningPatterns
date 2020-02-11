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
 * Criteria class for the {@link com.fime.domain.Group} entity. This class is used
 * in {@link com.fime.web.rest.GroupResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /groups?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GroupCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter groupCreatedBy;

    private LocalDateFilter groupCreationDate;

    private StringFilter groupModifiedBy;

    private LocalDateFilter groupModifiedDate;

    private LongFilter subjectId;

    private LongFilter teacherId;

    public GroupCriteria(){
    }

    public GroupCriteria(GroupCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.groupCreatedBy = other.groupCreatedBy == null ? null : other.groupCreatedBy.copy();
        this.groupCreationDate = other.groupCreationDate == null ? null : other.groupCreationDate.copy();
        this.groupModifiedBy = other.groupModifiedBy == null ? null : other.groupModifiedBy.copy();
        this.groupModifiedDate = other.groupModifiedDate == null ? null : other.groupModifiedDate.copy();
        this.subjectId = other.subjectId == null ? null : other.subjectId.copy();
        this.teacherId = other.teacherId == null ? null : other.teacherId.copy();
    }

    @Override
    public GroupCriteria copy() {
        return new GroupCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getGroupCreatedBy() {
        return groupCreatedBy;
    }

    public void setGroupCreatedBy(StringFilter groupCreatedBy) {
        this.groupCreatedBy = groupCreatedBy;
    }

    public LocalDateFilter getGroupCreationDate() {
        return groupCreationDate;
    }

    public void setGroupCreationDate(LocalDateFilter groupCreationDate) {
        this.groupCreationDate = groupCreationDate;
    }

    public StringFilter getGroupModifiedBy() {
        return groupModifiedBy;
    }

    public void setGroupModifiedBy(StringFilter groupModifiedBy) {
        this.groupModifiedBy = groupModifiedBy;
    }

    public LocalDateFilter getGroupModifiedDate() {
        return groupModifiedDate;
    }

    public void setGroupModifiedDate(LocalDateFilter groupModifiedDate) {
        this.groupModifiedDate = groupModifiedDate;
    }

    public LongFilter getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(LongFilter subjectId) {
        this.subjectId = subjectId;
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
        final GroupCriteria that = (GroupCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(groupCreatedBy, that.groupCreatedBy) &&
            Objects.equals(groupCreationDate, that.groupCreationDate) &&
            Objects.equals(groupModifiedBy, that.groupModifiedBy) &&
            Objects.equals(groupModifiedDate, that.groupModifiedDate) &&
            Objects.equals(subjectId, that.subjectId) &&
            Objects.equals(teacherId, that.teacherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        groupCreatedBy,
        groupCreationDate,
        groupModifiedBy,
        groupModifiedDate,
        subjectId,
        teacherId
        );
    }

    @Override
    public String toString() {
        return "GroupCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (groupCreatedBy != null ? "groupCreatedBy=" + groupCreatedBy + ", " : "") +
                (groupCreationDate != null ? "groupCreationDate=" + groupCreationDate + ", " : "") +
                (groupModifiedBy != null ? "groupModifiedBy=" + groupModifiedBy + ", " : "") +
                (groupModifiedDate != null ? "groupModifiedDate=" + groupModifiedDate + ", " : "") +
                (subjectId != null ? "subjectId=" + subjectId + ", " : "") +
                (teacherId != null ? "teacherId=" + teacherId + ", " : "") +
            "}";
    }

}
