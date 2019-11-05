package com.fime.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Activity.class)
public abstract class Activity_ {

	public static volatile SingularAttribute<Activity, Integer> activityUtility;
	public static volatile SingularAttribute<Activity, String> activityModifiedBy;
	public static volatile SingularAttribute<Activity, LocalDate> activityModifiedDate;
	public static volatile SingularAttribute<Activity, Integer> activityReqsId;
	public static volatile SingularAttribute<Activity, Subtheme> subtheme;
	public static volatile SingularAttribute<Activity, String> activityName;
	public static volatile SingularAttribute<Activity, Integer> activityDuration;
	public static volatile SingularAttribute<Activity, Long> id;
	public static volatile SingularAttribute<Activity, String> activityDescription;
	public static volatile SingularAttribute<Activity, LocalDate> activityCreationDate;
	public static volatile SingularAttribute<Activity, String> activityCreatedBy;

	public static final String ACTIVITY_UTILITY = "activityUtility";
	public static final String ACTIVITY_MODIFIED_BY = "activityModifiedBy";
	public static final String ACTIVITY_MODIFIED_DATE = "activityModifiedDate";
	public static final String ACTIVITY_REQS_ID = "activityReqsId";
	public static final String SUBTHEME = "subtheme";
	public static final String ACTIVITY_NAME = "activityName";
	public static final String ACTIVITY_DURATION = "activityDuration";
	public static final String ID = "id";
	public static final String ACTIVITY_DESCRIPTION = "activityDescription";
	public static final String ACTIVITY_CREATION_DATE = "activityCreationDate";
	public static final String ACTIVITY_CREATED_BY = "activityCreatedBy";

}

