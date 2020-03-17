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
 * Criteria class for the {@link com.fime.domain.Subtheme} entity. This class is used
 * in {@link com.fime.web.rest.SubthemeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /subthemes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SubthemeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter subthemeName;

    private StringFilter subthemeDescription;

    private StringFilter subthemeCreatedBy;

    private LocalDateFilter subthemeCreationDate;

    private StringFilter subthemeModifiedBy;

    private LocalDateFilter subthemeModifiedDate;

    private IntegerFilter subthemeMaxGrade;

    private LongFilter themeId;

    public SubthemeCriteria(){
    }

    public SubthemeCriteria(SubthemeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.subthemeName = other.subthemeName == null ? null : other.subthemeName.copy();
        this.subthemeDescription = other.subthemeDescription == null ? null : other.subthemeDescription.copy();
        this.subthemeCreatedBy = other.subthemeCreatedBy == null ? null : other.subthemeCreatedBy.copy();
        this.subthemeCreationDate = other.subthemeCreationDate == null ? null : other.subthemeCreationDate.copy();
        this.subthemeModifiedBy = other.subthemeModifiedBy == null ? null : other.subthemeModifiedBy.copy();
        this.subthemeModifiedDate = other.subthemeModifiedDate == null ? null : other.subthemeModifiedDate.copy();
        this.subthemeMaxGrade = other.subthemeMaxGrade == null ? null : other.subthemeMaxGrade.copy();
        this.themeId = other.themeId == null ? null : other.themeId.copy();
    }

    @Override
    public SubthemeCriteria copy() {
        return new SubthemeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSubthemeName() {
        return subthemeName;
    }

    public void setSubthemeName(StringFilter subthemeName) {
        this.subthemeName = subthemeName;
    }

    public StringFilter getSubthemeDescription() {
        return subthemeDescription;
    }

    public void setSubthemeDescription(StringFilter subthemeDescription) {
        this.subthemeDescription = subthemeDescription;
    }

    public StringFilter getSubthemeCreatedBy() {
        return subthemeCreatedBy;
    }

    public void setSubthemeCreatedBy(StringFilter subthemeCreatedBy) {
        this.subthemeCreatedBy = subthemeCreatedBy;
    }

    public LocalDateFilter getSubthemeCreationDate() {
        return subthemeCreationDate;
    }

    public void setSubthemeCreationDate(LocalDateFilter subthemeCreationDate) {
        this.subthemeCreationDate = subthemeCreationDate;
    }

    public StringFilter getSubthemeModifiedBy() {
        return subthemeModifiedBy;
    }

    public void setSubthemeModifiedBy(StringFilter subthemeModifiedBy) {
        this.subthemeModifiedBy = subthemeModifiedBy;
    }

    public LocalDateFilter getSubthemeModifiedDate() {
        return subthemeModifiedDate;
    }

    public void setSubthemeModifiedDate(LocalDateFilter subthemeModifiedDate) {
        this.subthemeModifiedDate = subthemeModifiedDate;
    }

    public IntegerFilter getSubthemeMaxGrade() {
        return subthemeMaxGrade;
    }

    public void setSubthemeMaxGrade(IntegerFilter subthemeMaxGrade) {
        this.subthemeMaxGrade = subthemeMaxGrade;
    }

    public LongFilter getThemeId() {
        return themeId;
    }

    public void setThemeId(LongFilter themeId) {
        this.themeId = themeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SubthemeCriteria that = (SubthemeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(subthemeName, that.subthemeName) &&
            Objects.equals(subthemeDescription, that.subthemeDescription) &&
            Objects.equals(subthemeCreatedBy, that.subthemeCreatedBy) &&
            Objects.equals(subthemeCreationDate, that.subthemeCreationDate) &&
            Objects.equals(subthemeModifiedBy, that.subthemeModifiedBy) &&
            Objects.equals(subthemeModifiedDate, that.subthemeModifiedDate) &&
            Objects.equals(subthemeMaxGrade, that.subthemeMaxGrade) &&
            Objects.equals(themeId, that.themeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        subthemeName,
        subthemeDescription,
        subthemeCreatedBy,
        subthemeCreationDate,
        subthemeModifiedBy,
        subthemeModifiedDate,
        subthemeMaxGrade,
        themeId
        );
    }

    @Override
    public String toString() {
        return "SubthemeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (subthemeName != null ? "subthemeName=" + subthemeName + ", " : "") +
                (subthemeDescription != null ? "subthemeDescription=" + subthemeDescription + ", " : "") +
                (subthemeCreatedBy != null ? "subthemeCreatedBy=" + subthemeCreatedBy + ", " : "") +
                (subthemeCreationDate != null ? "subthemeCreationDate=" + subthemeCreationDate + ", " : "") +
                (subthemeModifiedBy != null ? "subthemeModifiedBy=" + subthemeModifiedBy + ", " : "") +
                (subthemeModifiedDate != null ? "subthemeModifiedDate=" + subthemeModifiedDate + ", " : "") +
                (subthemeMaxGrade != null ? "subthemeMaxGrade=" + subthemeMaxGrade + ", " : "") +
                (themeId != null ? "themeId=" + themeId + ", " : "") +
            "}";
    }

}
