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
 * Criteria class for the {@link com.fime.domain.Theme} entity. This class is used
 * in {@link com.fime.web.rest.ThemeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /themes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ThemeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter themeName;

    private StringFilter themeDescription;

    private StringFilter themeCreatedBy;

    private LocalDateFilter themeCreationDate;

    private StringFilter themeModifiedBy;

    private LocalDateFilter themeModifiedDate;

    private LongFilter subjectId;

    public ThemeCriteria(){
    }

    public ThemeCriteria(ThemeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.themeName = other.themeName == null ? null : other.themeName.copy();
        this.themeDescription = other.themeDescription == null ? null : other.themeDescription.copy();
        this.themeCreatedBy = other.themeCreatedBy == null ? null : other.themeCreatedBy.copy();
        this.themeCreationDate = other.themeCreationDate == null ? null : other.themeCreationDate.copy();
        this.themeModifiedBy = other.themeModifiedBy == null ? null : other.themeModifiedBy.copy();
        this.themeModifiedDate = other.themeModifiedDate == null ? null : other.themeModifiedDate.copy();
        this.subjectId = other.subjectId == null ? null : other.subjectId.copy();
    }

    @Override
    public ThemeCriteria copy() {
        return new ThemeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getThemeName() {
        return themeName;
    }

    public void setThemeName(StringFilter themeName) {
        this.themeName = themeName;
    }

    public StringFilter getThemeDescription() {
        return themeDescription;
    }

    public void setThemeDescription(StringFilter themeDescription) {
        this.themeDescription = themeDescription;
    }

    public StringFilter getThemeCreatedBy() {
        return themeCreatedBy;
    }

    public void setThemeCreatedBy(StringFilter themeCreatedBy) {
        this.themeCreatedBy = themeCreatedBy;
    }

    public LocalDateFilter getThemeCreationDate() {
        return themeCreationDate;
    }

    public void setThemeCreationDate(LocalDateFilter themeCreationDate) {
        this.themeCreationDate = themeCreationDate;
    }

    public StringFilter getThemeModifiedBy() {
        return themeModifiedBy;
    }

    public void setThemeModifiedBy(StringFilter themeModifiedBy) {
        this.themeModifiedBy = themeModifiedBy;
    }

    public LocalDateFilter getThemeModifiedDate() {
        return themeModifiedDate;
    }

    public void setThemeModifiedDate(LocalDateFilter themeModifiedDate) {
        this.themeModifiedDate = themeModifiedDate;
    }

    public LongFilter getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(LongFilter subjectId) {
        this.subjectId = subjectId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ThemeCriteria that = (ThemeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(themeName, that.themeName) &&
            Objects.equals(themeDescription, that.themeDescription) &&
            Objects.equals(themeCreatedBy, that.themeCreatedBy) &&
            Objects.equals(themeCreationDate, that.themeCreationDate) &&
            Objects.equals(themeModifiedBy, that.themeModifiedBy) &&
            Objects.equals(themeModifiedDate, that.themeModifiedDate) &&
            Objects.equals(subjectId, that.subjectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        themeName,
        themeDescription,
        themeCreatedBy,
        themeCreationDate,
        themeModifiedBy,
        themeModifiedDate,
        subjectId
        );
    }

    @Override
    public String toString() {
        return "ThemeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (themeName != null ? "themeName=" + themeName + ", " : "") +
                (themeDescription != null ? "themeDescription=" + themeDescription + ", " : "") +
                (themeCreatedBy != null ? "themeCreatedBy=" + themeCreatedBy + ", " : "") +
                (themeCreationDate != null ? "themeCreationDate=" + themeCreationDate + ", " : "") +
                (themeModifiedBy != null ? "themeModifiedBy=" + themeModifiedBy + ", " : "") +
                (themeModifiedDate != null ? "themeModifiedDate=" + themeModifiedDate + ", " : "") +
                (subjectId != null ? "subjectId=" + subjectId + ", " : "") +
            "}";
    }

}
