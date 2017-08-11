package com.reqman.common;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.reqman.pojo.Project;
import com.reqman.pojo.Users;
 
public class HibernateUtil {
    private static SessionFactory sessionFactory;
	private static Session session;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            // loads configuration and mappings
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
    	    configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
    	    configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/reqman");
    	    configuration.setProperty("hibernate.connection.username", "postgres");
    	    configuration.setProperty("hibernate.connection.password", "3394");
    	    configuration.setProperty("hibernate.show_sql", "true");
    	    configuration.setProperty("hibernate.default_schema", "reqman");
    	    //configuration.setProperty("hibernate.hbm2ddl.auto", "update");
    	    
    	    configuration.addAnnotatedClass(Project.class);
            ServiceRegistry serviceRegistry
                = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
             
            // builds a session factory from the service registry
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);           
        }
         
        return sessionFactory;
    }
    
    public static Session getSession()
    {
    	session = HibernateUtil.getSessionFactory() != null 
    			? HibernateUtil.getSessionFactory().openSession() : null;
    			
		return session;
    }
    
    


}