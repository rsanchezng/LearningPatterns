package com.fime.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Teacher.class)
public abstract class Teacher_ {

	public static volatile SingularAttribute<Teacher, String> teacherModifiedBy;
	public static volatile SingularAttribute<Teacher, String> teacherEmail;
	public static volatile SingularAttribute<Teacher, String> teacherLastName;
	public static volatile SingularAttribute<Teacher, String> teacherPassword;
	public static volatile SingularAttribute<Teacher, String> teacherCreatedBy;
	public static volatile SingularAttribute<Teacher, LocalDate> teacherModifiedDate;
	public static volatile SingularAttribute<Teacher, Long> id;
	public static volatile SingularAttribute<Teacher, String> teacherFirstName;
	public static volatile SingularAttribute<Teacher, LocalDate> teacherCreationDate;

	public static final String TEACHER_MODIFIED_BY = "teacherModifiedBy";
	public static final String TEACHER_EMAIL = "teacherEmail";
	public static final String TEACHER_LAST_NAME = "teacherLastName";
	public static final String TEACHER_PASSWORD = "teacherPassword";
	public static final String TEACHER_CREATED_BY = "teacherCreatedBy";
	public static final String TEACHER_MODIFIED_DATE = "teacherModifiedDate";
	public static final String ID = "id";
	public static final String TEACHER_FIRST_NAME = "teacherFirstName";
	public static final String TEACHER_CREATION_DATE = "teacherCreationDate";

}

