package com.fime.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Subtheme.
 */
@Entity
@Table(name = "subtheme")
public class Subtheme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "subtheme_name")
    private String subthemeName;

    @Column(name = "subtheme_description")
    private String subthemeDescription;

    @Column(name = "subtheme_created_by")
    private String subthemeCreatedBy;

    @Column(name = "subtheme_creation_date")
    private LocalDate subthemeCreationDate;

    @Column(name = "subtheme_modified_by")
    private String subthemeModifiedBy;

    @Column(name = "subtheme_modified_date")
    private LocalDate subthemeModifiedDate;

    @ManyToOne
    @JsonIgnoreProperties("subthemes")
    private Theme theme;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubthemeName() {
        return subthemeName;
    }

    public Subtheme subthemeName(String subthemeName) {
        this.subthemeName = subthemeName;
        return this;
    }

    public void setSubthemeName(String subthemeName) {
        this.subthemeName = subthemeName;
    }

    public String getSubthemeDescription() {
        return subthemeDescription;
    }

    public Subtheme subthemeDescription(String subthemeDescription) {
        this.subthemeDescription = subthemeDescription;
        return this;
    }

    public void setSubthemeDescription(String subthemeDescription) {
        this.subthemeDescription = subthemeDescription;
    }

    public String getSubthemeCreatedBy() {
        return subthemeCreatedBy;
    }

    public Subtheme subthemeCreatedBy(String subthemeCreatedBy) {
        this.subthemeCreatedBy = subthemeCreatedBy;
        return this;
    }

    public void setSubthemeCreatedBy(String subthemeCreatedBy) {
        this.subthemeCreatedBy = subthemeCreatedBy;
    }

    public LocalDate getSubthemeCreationDate() {
        return subthemeCreationDate;
    }

    public Subtheme subthemeCreationDate(LocalDate subthemeCreationDate) {
        this.subthemeCreationDate = subthemeCreationDate;
        return this;
    }

    public void setSubthemeCreationDate(LocalDate subthemeCreationDate) {
        this.subthemeCreationDate = subthemeCreationDate;
    }

    public String getSubthemeModifiedBy() {
        return subthemeModifiedBy;
    }

    public Subtheme subthemeModifiedBy(String subthemeModifiedBy) {
        this.subthemeModifiedBy = subthemeModifiedBy;
        return this;
    }

    public void setSubthemeModifiedBy(String subthemeModifiedBy) {
        this.subthemeModifiedBy = subthemeModifiedBy;
    }

    public LocalDate getSubthemeModifiedDate() {
        return subthemeModifiedDate;
    }

    public Subtheme subthemeModifiedDate(LocalDate subthemeModifiedDate) {
        this.subthemeModifiedDate = subthemeModifiedDate;
        return this;
    }

    public void setSubthemeModifiedDate(LocalDate subthemeModifiedDate) {
        this.subthemeModifiedDate = subthemeModifiedDate;
    }

    public Theme getTheme() {
        return theme;
    }

    public Subtheme theme(Theme theme) {
        this.theme = theme;
        return this;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subtheme)) {
            return false;
        }
        return id != null && id.equals(((Subtheme) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Subtheme{" +
            "id=" + getId() +
            ", subthemeName='" + getSubthemeName() + "'" +
            ", subthemeDescription='" + getSubthemeDescription() + "'" +
            ", subthemeCreatedBy='" + getSubthemeCreatedBy() + "'" +
            ", subthemeCreationDate='" + getSubthemeCreationDate() + "'" +
            ", subthemeModifiedBy='" + getSubthemeModifiedBy() + "'" +
            ", subthemeModifiedDate='" + getSubthemeModifiedDate() + "'" +
            "}";
    }
}
