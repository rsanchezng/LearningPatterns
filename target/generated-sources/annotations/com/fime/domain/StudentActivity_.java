package com.fime.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StudentActivity.class)
public abstract class StudentActivity_ {

	public static volatile SingularAttribute<StudentActivity, LocalDate> activityStartDate;
	public static volatile SingularAttribute<StudentActivity, Integer> activityGrade;
	public static volatile SingularAttribute<StudentActivity, Activity> activity;
	public static volatile SingularAttribute<StudentActivity, Student> student;
	public static volatile SingularAttribute<StudentActivity, LocalDate> studentActivityGradeDate;
	public static volatile SingularAttribute<StudentActivity, LocalDate> activityEndDate;
	public static volatile SingularAttribute<StudentActivity, LocalDate> studentActivityCreatedDate;
	public static volatile SingularAttribute<StudentActivity, Long> id;
	public static volatile SingularAttribute<StudentActivity, String> studentActivityCreatedBy;
	public static volatile SingularAttribute<StudentActivity, LocalDate> studentActivityModifiedDate;
	public static volatile SingularAttribute<StudentActivity, LocalDate> studentActivityModifiedBy;

	public static final String ACTIVITY_START_DATE = "activityStartDate";
	public static final String ACTIVITY_GRADE = "activityGrade";
	public static final String ACTIVITY = "activity";
	public static final String STUDENT = "student";
	public static final String STUDENT_ACTIVITY_GRADE_DATE = "studentActivityGradeDate";
	public static final String ACTIVITY_END_DATE = "activityEndDate";
	public static final String STUDENT_ACTIVITY_CREATED_DATE = "studentActivityCreatedDate";
	public static final String ID = "id";
	public static final String STUDENT_ACTIVITY_CREATED_BY = "studentActivityCreatedBy";
	public static final String STUDENT_ACTIVITY_MODIFIED_DATE = "studentActivityModifiedDate";
	public static final String STUDENT_ACTIVITY_MODIFIED_BY = "studentActivityModifiedBy";

}

