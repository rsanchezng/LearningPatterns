package com.fime.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Activity.
 */
@Entity
@Table(name = "activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "activity_name")
    private String activityName;

    @Column(name = "activity_description")
    private String activityDescription;

    @Column(name = "activity_duration")
    private Integer activityDuration;

    @Column(name = "activity_utility")
    private Integer activityUtility;

    @Column(name = "activity_reqs_id")
    private Integer activityReqsId;

    @Column(name = "activity_created_by")
    private String activityCreatedBy;

    @Column(name = "activity_creation_date")
    private LocalDate activityCreationDate;

    @Column(name = "activity_modified_by")
    private String activityModifiedBy;

    @Column(name = "activity_modified_date")
    private LocalDate activityModifiedDate;

    @ManyToOne
    @JsonIgnoreProperties("activities")
    private Subtheme subtheme;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public Activity activityName(String activityName) {
        this.activityName = activityName;
        return this;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public Activity activityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
        return this;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public Integer getActivityDuration() {
        return activityDuration;
    }

    public Activity activityDuration(Integer activityDuration) {
        this.activityDuration = activityDuration;
        return this;
    }

    public void setActivityDuration(Integer activityDuration) {
        this.activityDuration = activityDuration;
    }

    public Integer getActivityUtility() {
        return activityUtility;
    }

    public Activity activityUtility(Integer activityUtility) {
        this.activityUtility = activityUtility;
        return this;
    }

    public void setActivityUtility(Integer activityUtility) {
        this.activityUtility = activityUtility;
    }

    public Integer getActivityReqsId() {
        return activityReqsId;
    }

    public Activity activityReqsId(Integer activityReqsId) {
        this.activityReqsId = activityReqsId;
        return this;
    }

    public void setActivityReqsId(Integer activityReqsId) {
        this.activityReqsId = activityReqsId;
    }

    public String getActivityCreatedBy() {
        return activityCreatedBy;
    }

    public Activity activityCreatedBy(String activityCreatedBy) {
        this.activityCreatedBy = activityCreatedBy;
        return this;
    }

    public void setActivityCreatedBy(String activityCreatedBy) {
        this.activityCreatedBy = activityCreatedBy;
    }

    public LocalDate getActivityCreationDate() {
        return activityCreationDate;
    }

    public Activity activityCreationDate(LocalDate activityCreationDate) {
        this.activityCreationDate = activityCreationDate;
        return this;
    }

    public void setActivityCreationDate(LocalDate activityCreationDate) {
        this.activityCreationDate = activityCreationDate;
    }

    public String getActivityModifiedBy() {
        return activityModifiedBy;
    }

    public Activity activityModifiedBy(String activityModifiedBy) {
        this.activityModifiedBy = activityModifiedBy;
        return this;
    }

    public void setActivityModifiedBy(String activityModifiedBy) {
        this.activityModifiedBy = activityModifiedBy;
    }

    public LocalDate getActivityModifiedDate() {
        return activityModifiedDate;
    }

    public Activity activityModifiedDate(LocalDate activityModifiedDate) {
        this.activityModifiedDate = activityModifiedDate;
        return this;
    }

    public void setActivityModifiedDate(LocalDate activityModifiedDate) {
        this.activityModifiedDate = activityModifiedDate;
    }

    public Subtheme getSubtheme() {
        return subtheme;
    }

    public Activity subtheme(Subtheme subtheme) {
        this.subtheme = subtheme;
        return this;
    }

    public void setSubtheme(Subtheme subtheme) {
        this.subtheme = subtheme;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Activity)) {
            return false;
        }
        return id != null && id.equals(((Activity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Activity{" +
            "id=" + getId() +
            ", activityName='" + getActivityName() + "'" +
            ", activityDescription='" + getActivityDescription() + "'" +
            ", activityDuration=" + getActivityDuration() +
            ", activityUtility=" + getActivityUtility() +
            ", activityReqsId=" + getActivityReqsId() +
            ", activityCreatedBy='" + getActivityCreatedBy() + "'" +
            ", activityCreationDate='" + getActivityCreationDate() + "'" +
            ", activityModifiedBy='" + getActivityModifiedBy() + "'" +
            ", activityModifiedDate='" + getActivityModifiedDate() + "'" +
            "}";
    }
}
