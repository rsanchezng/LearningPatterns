package com.fime.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Subtheme.class)
public abstract class Subtheme_ {

	public static volatile SingularAttribute<Subtheme, LocalDate> subthemeModifiedDate;
	public static volatile SingularAttribute<Subtheme, String> subthemeName;
	public static volatile SingularAttribute<Subtheme, LocalDate> subthemeCreationDate;
	public static volatile SingularAttribute<Subtheme, String> subthemeCreatedBy;
	public static volatile SingularAttribute<Subtheme, String> subthemeModifiedBy;
	public static volatile SingularAttribute<Subtheme, Theme> theme;
	public static volatile SingularAttribute<Subtheme, Long> id;
	public static volatile SingularAttribute<Subtheme, String> subthemeDescription;

	public static final String SUBTHEME_MODIFIED_DATE = "subthemeModifiedDate";
	public static final String SUBTHEME_NAME = "subthemeName";
	public static final String SUBTHEME_CREATION_DATE = "subthemeCreationDate";
	public static final String SUBTHEME_CREATED_BY = "subthemeCreatedBy";
	public static final String SUBTHEME_MODIFIED_BY = "subthemeModifiedBy";
	public static final String THEME = "theme";
	public static final String ID = "id";
	public static final String SUBTHEME_DESCRIPTION = "subthemeDescription";

}

