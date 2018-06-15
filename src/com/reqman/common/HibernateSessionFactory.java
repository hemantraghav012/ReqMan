package com.reqman.common;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateSessionFactory {

	/** The session factory. */
	private static SessionFactory sessionFactory;
	
	/** The Constant FilePath. */
	private static final String FilePath = "com/reqman/dao/hibernate.cfg.xml";
	
	/**
	 * Hibernate Session Factory Constructor.
	 */
	protected HibernateSessionFactory() {

	}

	/**
	 * This method returns session factory object.
	 * 
	 * @return SessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			// below changes are done for Thread Safe Singleton object Creation of Sessionfactory.
			synchronized (HibernateSessionFactory.class) {
				if (sessionFactory == null) {

					// loads configuration and mappings
					try {
						Configuration configuration = new Configuration()
						.configure(FilePath);
						StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties());
						sessionFactory = configuration.buildSessionFactory(builder
								.build());
					} catch (Throwable ex) {
						// Make sure you log the exception, as it might be swallowed
						throw new ExceptionInInitializerError(ex);
					}
				}
			}
		}
		return sessionFactory;
	}

	public static System getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
}
