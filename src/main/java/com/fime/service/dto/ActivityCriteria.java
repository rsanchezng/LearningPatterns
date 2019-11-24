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
 * Criteria class for the {@link com.fime.domain.Activity} entity. This class is used
 * in {@link com.fime.web.rest.ActivityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /activities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ActivityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter activityName;

    private StringFilter activityDescription;

    private IntegerFilter activityDuration;

    private IntegerFilter activityUtility;

    private IntegerFilter activityReqsId;

    private StringFilter activityCreatedBy;

    private LocalDateFilter activityCreationDate;

    private StringFilter activityModifiedBy;

    private LocalDateFilter activityModifiedDate;

    private LongFilter subthemeId;

    public ActivityCriteria(){
    }

    public ActivityCriteria(ActivityCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.activityName = other.activityName == null ? null : other.activityName.copy();
        this.activityDescription = other.activityDescription == null ? null : other.activityDescription.copy();
        this.activityDuration = other.activityDuration == null ? null : other.activityDuration.copy();
        this.activityUtility = other.activityUtility == null ? null : other.activityUtility.copy();
        this.activityReqsId = other.activityReqsId == null ? null : other.activityReqsId.copy();
        this.activityCreatedBy = other.activityCreatedBy == null ? null : other.activityCreatedBy.copy();
        this.activityCreationDate = other.activityCreationDate == null ? null : other.activityCreationDate.copy();
        this.activityModifiedBy = other.activityModifiedBy == null ? null : other.activityModifiedBy.copy();
        this.activityModifiedDate = other.activityModifiedDate == null ? null : other.activityModifiedDate.copy();
        this.subthemeId = other.subthemeId == null ? null : other.subthemeId.copy();
    }

    @Override
    public ActivityCriteria copy() {
        return new ActivityCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getActivityName() {
        return activityName;
    }

    public void setActivityName(StringFilter activityName) {
        this.activityName = activityName;
    }

    public StringFilter getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(StringFilter activityDescription) {
        this.activityDescription = activityDescription;
    }

    public IntegerFilter getActivityDuration() {
        return activityDuration;
    }

    public void setActivityDuration(IntegerFilter activityDuration) {
        this.activityDuration = activityDuration;
    }

    public IntegerFilter getActivityUtility() {
        return activityUtility;
    }

    public void setActivityUtility(IntegerFilter activityUtility) {
        this.activityUtility = activityUtility;
    }

    public IntegerFilter getActivityReqsId() {
        return activityReqsId;
    }

    public void setActivityReqsId(IntegerFilter activityReqsId) {
        this.activityReqsId = activityReqsId;
    }

    public StringFilter getActivityCreatedBy() {
        return activityCreatedBy;
    }

    public void setActivityCreatedBy(StringFilter activityCreatedBy) {
        this.activityCreatedBy = activityCreatedBy;
    }

    public LocalDateFilter getActivityCreationDate() {
        return activityCreationDate;
    }

    public void setActivityCreationDate(LocalDateFilter activityCreationDate) {
        this.activityCreationDate = activityCreationDate;
    }

    public StringFilter getActivityModifiedBy() {
        return activityModifiedBy;
    }

    public void setActivityModifiedBy(StringFilter activityModifiedBy) {
        this.activityModifiedBy = activityModifiedBy;
    }

    public LocalDateFilter getActivityModifiedDate() {
        return activityModifiedDate;
    }

    public void setActivityModifiedDate(LocalDateFilter activityModifiedDate) {
        this.activityModifiedDate = activityModifiedDate;
    }

    public LongFilter getSubthemeId() {
        return subthemeId;
    }

    public void setSubthemeId(LongFilter subthemeId) {
        this.subthemeId = subthemeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ActivityCriteria that = (ActivityCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(activityName, that.activityName) &&
            Objects.equals(activityDescription, that.activityDescription) &&
            Objects.equals(activityDuration, that.activityDuration) &&
            Objects.equals(activityUtility, that.activityUtility) &&
            Objects.equals(activityReqsId, that.activityReqsId) &&
            Objects.equals(activityCreatedBy, that.activityCreatedBy) &&
            Objects.equals(activityCreationDate, that.activityCreationDate) &&
            Objects.equals(activityModifiedBy, that.activityModifiedBy) &&
            Objects.equals(activityModifiedDate, that.activityModifiedDate) &&
            Objects.equals(subthemeId, that.subthemeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        activityName,
        activityDescription,
        activityDuration,
        activityUtility,
        activityReqsId,
        activityCreatedBy,
        activityCreationDate,
        activityModifiedBy,
        activityModifiedDate,
        subthemeId
        );
    }

    @Override
    public String toString() {
        return "ActivityCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (activityName != null ? "activityName=" + activityName + ", " : "") +
                (activityDescription != null ? "activityDescription=" + activityDescription + ", " : "") +
                (activityDuration != null ? "activityDuration=" + activityDuration + ", " : "") +
                (activityUtility != null ? "activityUtility=" + activityUtility + ", " : "") +
                (activityReqsId != null ? "activityReqsId=" + activityReqsId + ", " : "") +
                (activityCreatedBy != null ? "activityCreatedBy=" + activityCreatedBy + ", " : "") +
                (activityCreationDate != null ? "activityCreationDate=" + activityCreationDate + ", " : "") +
                (activityModifiedBy != null ? "activityModifiedBy=" + activityModifiedBy + ", " : "") +
                (activityModifiedDate != null ? "activityModifiedDate=" + activityModifiedDate + ", " : "") +
                (subthemeId != null ? "subthemeId=" + subthemeId + ", " : "") +
            "}";
    }

}
