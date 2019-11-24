package com.fime.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Group.
 */
@Entity
@Table(name = "jhi_group")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "group_created_by")
    private String groupCreatedBy;

    @Column(name = "group_creation_date")
    private LocalDate groupCreationDate;

    @Column(name = "group_modified_by")
    private String groupModifiedBy;

    @Column(name = "group_modified_date")
    private LocalDate groupModifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("groups")
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("groups")
    private Teacher teacher;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupCreatedBy() {
        return groupCreatedBy;
    }

    public Group groupCreatedBy(String groupCreatedBy) {
        this.groupCreatedBy = groupCreatedBy;
        return this;
    }

    public void setGroupCreatedBy(String groupCreatedBy) {
        this.groupCreatedBy = groupCreatedBy;
    }

    public LocalDate getGroupCreationDate() {
        return groupCreationDate;
    }

    public Group groupCreationDate(LocalDate groupCreationDate) {
        this.groupCreationDate = groupCreationDate;
        return this;
    }

    public void setGroupCreationDate(LocalDate groupCreationDate) {
        this.groupCreationDate = groupCreationDate;
    }

    public String getGroupModifiedBy() {
        return groupModifiedBy;
    }

    public Group groupModifiedBy(String groupModifiedBy) {
        this.groupModifiedBy = groupModifiedBy;
        return this;
    }

    public void setGroupModifiedBy(String groupModifiedBy) {
        this.groupModifiedBy = groupModifiedBy;
    }

    public LocalDate getGroupModifiedDate() {
        return groupModifiedDate;
    }

    public Group groupModifiedDate(LocalDate groupModifiedDate) {
        this.groupModifiedDate = groupModifiedDate;
        return this;
    }

    public void setGroupModifiedDate(LocalDate groupModifiedDate) {
        this.groupModifiedDate = groupModifiedDate;
    }

    public Subject getSubject() {
        return subject;
    }

    public Group subject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Group teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Group)) {
            return false;
        }
        return id != null && id.equals(((Group) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Group{" +
            "id=" + getId() +
            ", groupCreatedBy='" + getGroupCreatedBy() + "'" +
            ", groupCreationDate='" + getGroupCreationDate() + "'" +
            ", groupModifiedBy='" + getGroupModifiedBy() + "'" +
            ", groupModifiedDate='" + getGroupModifiedDate() + "'" +
            "}";
    }
}
