package com.fime.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Group.class)
public abstract class Group_ {

	public static volatile SingularAttribute<Group, LocalDate> groupCreationDate;
	public static volatile SingularAttribute<Group, Teacher> teacher;
	public static volatile SingularAttribute<Group, Subject> subject;
	public static volatile SingularAttribute<Group, String> groupModifiedBy;
	public static volatile SingularAttribute<Group, Long> id;
	public static volatile SingularAttribute<Group, LocalDate> groupModifiedDate;
	public static volatile SingularAttribute<Group, String> groupCreatedBy;

	public static final String GROUP_CREATION_DATE = "groupCreationDate";
	public static final String TEACHER = "teacher";
	public static final String SUBJECT = "subject";
	public static final String GROUP_MODIFIED_BY = "groupModifiedBy";
	public static final String ID = "id";
	public static final String GROUP_MODIFIED_DATE = "groupModifiedDate";
	public static final String GROUP_CREATED_BY = "groupCreatedBy";

}

