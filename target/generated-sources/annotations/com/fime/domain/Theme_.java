package com.fime.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Theme.class)
public abstract class Theme_ {

	public static volatile SingularAttribute<Theme, String> themeCreatedBy;
	public static volatile SingularAttribute<Theme, String> themeName;
	public static volatile SingularAttribute<Theme, Subject> subject;
	public static volatile SingularAttribute<Theme, LocalDate> themeModifiedDate;
	public static volatile SingularAttribute<Theme, String> themeDescription;
	public static volatile SingularAttribute<Theme, String> themeModifiedBy;
	public static volatile SingularAttribute<Theme, Long> id;
	public static volatile SingularAttribute<Theme, LocalDate> themeCreationDate;

	public static final String THEME_CREATED_BY = "themeCreatedBy";
	public static final String THEME_NAME = "themeName";
	public static final String SUBJECT = "subject";
	public static final String THEME_MODIFIED_DATE = "themeModifiedDate";
	public static final String THEME_DESCRIPTION = "themeDescription";
	public static final String THEME_MODIFIED_BY = "themeModifiedBy";
	public static final String ID = "id";
	public static final String THEME_CREATION_DATE = "themeCreationDate";

}

