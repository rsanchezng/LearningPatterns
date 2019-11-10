package com.fime.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Theme.
 */
@Entity
@Table(name = "theme")
public class Theme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "theme_name")
    private String themeName;

    @Column(name = "theme_description")
    private String themeDescription;

    @Column(name = "theme_created_by")
    private String themeCreatedBy;

    @Column(name = "theme_creation_date")
    private LocalDate themeCreationDate;

    @Column(name = "theme_modified_by")
    private String themeModifiedBy;

    @Column(name = "theme_modified_date")
    private LocalDate themeModifiedDate;

    @ManyToOne
    @JsonIgnoreProperties("themes")
    private Subject subject;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThemeName() {
        return themeName;
    }

    public Theme themeName(String themeName) {
        this.themeName = themeName;
        return this;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemeDescription() {
        return themeDescription;
    }

    public Theme themeDescription(String themeDescription) {
        this.themeDescription = themeDescription;
        return this;
    }

    public void setThemeDescription(String themeDescription) {
        this.themeDescription = themeDescription;
    }

    public String getThemeCreatedBy() {
        return themeCreatedBy;
    }

    public Theme themeCreatedBy(String themeCreatedBy) {
        this.themeCreatedBy = themeCreatedBy;
        return this;
    }

    public void setThemeCreatedBy(String themeCreatedBy) {
        this.themeCreatedBy = themeCreatedBy;
    }

    public LocalDate getThemeCreationDate() {
        return themeCreationDate;
    }

    public Theme themeCreationDate(LocalDate themeCreationDate) {
        this.themeCreationDate = themeCreationDate;
        return this;
    }

    public void setThemeCreationDate(LocalDate themeCreationDate) {
        this.themeCreationDate = themeCreationDate;
    }

    public String getThemeModifiedBy() {
        return themeModifiedBy;
    }

    public Theme themeModifiedBy(String themeModifiedBy) {
        this.themeModifiedBy = themeModifiedBy;
        return this;
    }

    public void setThemeModifiedBy(String themeModifiedBy) {
        this.themeModifiedBy = themeModifiedBy;
    }

    public LocalDate getThemeModifiedDate() {
        return themeModifiedDate;
    }

    public Theme themeModifiedDate(LocalDate themeModifiedDate) {
        this.themeModifiedDate = themeModifiedDate;
        return this;
    }

    public void setThemeModifiedDate(LocalDate themeModifiedDate) {
        this.themeModifiedDate = themeModifiedDate;
    }

    public Subject getSubject() {
        return subject;
    }

    public Theme subject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Theme)) {
            return false;
        }
        return id != null && id.equals(((Theme) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Theme{" +
            "id=" + getId() +
            ", themeName='" + getThemeName() + "'" +
            ", themeDescription='" + getThemeDescription() + "'" +
            ", themeCreatedBy='" + getThemeCreatedBy() + "'" +
            ", themeCreationDate='" + getThemeCreationDate() + "'" +
            ", themeModifiedBy='" + getThemeModifiedBy() + "'" +
            ", themeModifiedDate='" + getThemeModifiedDate() + "'" +
            "}";
    }
}
