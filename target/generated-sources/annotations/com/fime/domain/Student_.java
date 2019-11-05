package com.fime.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Student.class)
public abstract class Student_ {

	public static volatile SingularAttribute<Student, String> studentLastName;
	public static volatile SingularAttribute<Student, String> studentPassword;
	public static volatile SingularAttribute<Student, String> studentEmail;
	public static volatile SingularAttribute<Student, LocalDate> studentCreationDate;
	public static volatile SingularAttribute<Student, String> studentCredits;
	public static volatile SingularAttribute<Student, String> studentCreatedBy;
	public static volatile SingularAttribute<Student, Long> id;
	public static volatile SingularAttribute<Student, LocalDate> studentModifiedDate;
	public static volatile SingularAttribute<Student, String> studentFirstName;
	public static volatile SingularAttribute<Student, String> studentModifiedBy;

	public static final String STUDENT_LAST_NAME = "studentLastName";
	public static final String STUDENT_PASSWORD = "studentPassword";
	public static final String STUDENT_EMAIL = "studentEmail";
	public static final String STUDENT_CREATION_DATE = "studentCreationDate";
	public static final String STUDENT_CREDITS = "studentCredits";
	public static final String STUDENT_CREATED_BY = "studentCreatedBy";
	public static final String ID = "id";
	public static final String STUDENT_MODIFIED_DATE = "studentModifiedDate";
	public static final String STUDENT_FIRST_NAME = "studentFirstName";
	public static final String STUDENT_MODIFIED_BY = "studentModifiedBy";

}

