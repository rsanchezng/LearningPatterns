package com.fime.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Subject.class)
public abstract class Subject_ {

	public static volatile SingularAttribute<Subject, LocalDate> subjectModifiedDate;
	public static volatile SingularAttribute<Subject, String> subjectCreatedBy;
	public static volatile SingularAttribute<Subject, String> subjectModifiedBy;
	public static volatile SingularAttribute<Subject, Teacher> teacher;
	public static volatile SingularAttribute<Subject, String> subjectDescription;
	public static volatile SingularAttribute<Subject, Integer> subjectCredits;
	public static volatile SingularAttribute<Subject, Long> id;
	public static volatile SingularAttribute<Subject, LocalDate> subjectCreationDate;
	public static volatile SingularAttribute<Subject, String> subjectName;

	public static final String SUBJECT_MODIFIED_DATE = "subjectModifiedDate";
	public static final String SUBJECT_CREATED_BY = "subjectCreatedBy";
	public static final String SUBJECT_MODIFIED_BY = "subjectModifiedBy";
	public static final String TEACHER = "teacher";
	public static final String SUBJECT_DESCRIPTION = "subjectDescription";
	public static final String SUBJECT_CREDITS = "subjectCredits";
	public static final String ID = "id";
	public static final String SUBJECT_CREATION_DATE = "subjectCreationDate";
	public static final String SUBJECT_NAME = "subjectName";

}

