package com.fime.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StudentSchedule.class)
public abstract class StudentSchedule_ {

	public static volatile SingularAttribute<StudentSchedule, LocalDate> scheduleModifiedDate;
	public static volatile SingularAttribute<StudentSchedule, String> scheduleCreatedBy;
	public static volatile SingularAttribute<StudentSchedule, LocalDate> scheduleCreationDate;
	public static volatile SingularAttribute<StudentSchedule, Student> student;
	public static volatile SingularAttribute<StudentSchedule, Long> id;
	public static volatile SingularAttribute<StudentSchedule, String> scheduleModifiedBy;
	public static volatile SingularAttribute<StudentSchedule, Group> group;

	public static final String SCHEDULE_MODIFIED_DATE = "scheduleModifiedDate";
	public static final String SCHEDULE_CREATED_BY = "scheduleCreatedBy";
	public static final String SCHEDULE_CREATION_DATE = "scheduleCreationDate";
	public static final String STUDENT = "student";
	public static final String ID = "id";
	public static final String SCHEDULE_MODIFIED_BY = "scheduleModifiedBy";
	public static final String GROUP = "group";

}

