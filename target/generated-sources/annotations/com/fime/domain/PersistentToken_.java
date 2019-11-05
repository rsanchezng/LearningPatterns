package com.fime.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PersistentToken.class)
public abstract class PersistentToken_ {

	public static volatile SingularAttribute<PersistentToken, LocalDate> tokenDate;
	public static volatile SingularAttribute<PersistentToken, String> series;
	public static volatile SingularAttribute<PersistentToken, String> ipAddress;
	public static volatile SingularAttribute<PersistentToken, String> userAgent;
	public static volatile SingularAttribute<PersistentToken, String> tokenValue;
	public static volatile SingularAttribute<PersistentToken, User> user;

	public static final String TOKEN_DATE = "tokenDate";
	public static final String SERIES = "series";
	public static final String IP_ADDRESS = "ipAddress";
	public static final String USER_AGENT = "userAgent";
	public static final String TOKEN_VALUE = "tokenValue";
	public static final String USER = "user";

}

