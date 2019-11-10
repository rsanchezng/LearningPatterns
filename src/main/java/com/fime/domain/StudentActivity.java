package com.fime.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A StudentActivity.
 */
@Entity
@Table(name = "student_activity")
public class StudentActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "activity_start_date")
    private LocalDate activityStartDate;

    @Column(name = "activity_end_date")
    private LocalDate activityEndDate;

    @Column(name = "activity_grade")
    private Integer activityGrade;

    @Column(name = "student_activity_grade_date")
    private LocalDate studentActivityGradeDate;

    @Column(name = "student_activity_created_date")
    private LocalDate studentActivityCreatedDate;

    @Column(name = "student_activity_created_by")
    private String studentActivityCreatedBy;

    @Column(name = "student_activity_modified_date")
    private LocalDate studentActivityModifiedDate;

    @Column(name = "student_activity_modified_by")
    private LocalDate studentActivityModifiedBy;

    @ManyToOne
    @JsonIgnoreProperties("studentActivities")
    private Activity activity;

    @ManyToOne
    @JsonIgnoreProperties("studentActivities")
    private StudentSchedule studentschedule;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getActivityStartDate() {
        return activityStartDate;
    }

    public StudentActivity activityStartDate(LocalDate activityStartDate) {
        this.activityStartDate = activityStartDate;
        return this;
    }

    public void setActivityStartDate(LocalDate activityStartDate) {
        this.activityStartDate = activityStartDate;
    }

    public LocalDate getActivityEndDate() {
        return activityEndDate;
    }

    public StudentActivity activityEndDate(LocalDate activityEndDate) {
        this.activityEndDate = activityEndDate;
        return this;
    }

    public void setActivityEndDate(LocalDate activityEndDate) {
        this.activityEndDate = activityEndDate;
    }

    public Integer getActivityGrade() {
        return activityGrade;
    }

    public StudentActivity activityGrade(Integer activityGrade) {
        this.activityGrade = activityGrade;
        return this;
    }

    public void setActivityGrade(Integer activityGrade) {
        this.activityGrade = activityGrade;
    }

    public LocalDate getStudentActivityGradeDate() {
        return studentActivityGradeDate;
    }

    public StudentActivity studentActivityGradeDate(LocalDate studentActivityGradeDate) {
        this.studentActivityGradeDate = studentActivityGradeDate;
        return this;
    }

    public void setStudentActivityGradeDate(LocalDate studentActivityGradeDate) {
        this.studentActivityGradeDate = studentActivityGradeDate;
    }

    public LocalDate getStudentActivityCreatedDate() {
        return studentActivityCreatedDate;
    }

    public StudentActivity studentActivityCreatedDate(LocalDate studentActivityCreatedDate) {
        this.studentActivityCreatedDate = studentActivityCreatedDate;
        return this;
    }

    public void setStudentActivityCreatedDate(LocalDate studentActivityCreatedDate) {
        this.studentActivityCreatedDate = studentActivityCreatedDate;
    }

    public String getStudentActivityCreatedBy() {
        return studentActivityCreatedBy;
    }

    public StudentActivity studentActivityCreatedBy(String studentActivityCreatedBy) {
        this.studentActivityCreatedBy = studentActivityCreatedBy;
        return this;
    }

    public void setStudentActivityCreatedBy(String studentActivityCreatedBy) {
        this.studentActivityCreatedBy = studentActivityCreatedBy;
    }

    public LocalDate getStudentActivityModifiedDate() {
        return studentActivityModifiedDate;
    }

    public StudentActivity studentActivityModifiedDate(LocalDate studentActivityModifiedDate) {
        this.studentActivityModifiedDate = studentActivityModifiedDate;
        return this;
    }

    public void setStudentActivityModifiedDate(LocalDate studentActivityModifiedDate) {
        this.studentActivityModifiedDate = studentActivityModifiedDate;
    }

    public LocalDate getStudentActivityModifiedBy() {
        return studentActivityModifiedBy;
    }

    public StudentActivity studentActivityModifiedBy(LocalDate studentActivityModifiedBy) {
        this.studentActivityModifiedBy = studentActivityModifiedBy;
        return this;
    }

    public void setStudentActivityModifiedBy(LocalDate studentActivityModifiedBy) {
        this.studentActivityModifiedBy = studentActivityModifiedBy;
    }

    public Activity getActivity() {
        return activity;
    }

    public StudentActivity activity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public StudentSchedule getStudentschedule() {
        return studentschedule;
    }

    public StudentActivity studentschedule(StudentSchedule studentSchedule) {
        this.studentschedule = studentSchedule;
        return this;
    }

    public void setStudentschedule(StudentSchedule studentSchedule) {
        this.studentschedule = studentSchedule;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentActivity)) {
            return false;
        }
        return id != null && id.equals(((StudentActivity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StudentActivity{" +
            "id=" + getId() +
            ", activityStartDate='" + getActivityStartDate() + "'" +
            ", activityEndDate='" + getActivityEndDate() + "'" +
            ", activityGrade=" + getActivityGrade() +
            ", studentActivityGradeDate='" + getStudentActivityGradeDate() + "'" +
            ", studentActivityCreatedDate='" + getStudentActivityCreatedDate() + "'" +
            ", studentActivityCreatedBy='" + getStudentActivityCreatedBy() + "'" +
            ", studentActivityModifiedDate='" + getStudentActivityModifiedDate() + "'" +
            ", studentActivityModifiedBy='" + getStudentActivityModifiedBy() + "'" +
            "}";
    }
}
