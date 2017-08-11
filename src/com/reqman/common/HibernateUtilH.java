package com.reqman.common;

import java.net.URI;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.reqman.pojo.Category;
import com.reqman.pojo.Project;
import com.reqman.pojo.Users;
 
public class HibernateUtilH {
    private static SessionFactory sessionFactory;
	private static Session session;

    public static SessionFactory getSessionFactory() {
    	
    	try
    	{
            if (sessionFactory == null) {
            	
            	URI dbUri = new URI(System.getenv("DATABASE_URL"));

            	
                // loads configuration and mappings
                Configuration configuration = new Configuration();
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        	    configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        	    configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://"+ dbUri.getHost() +":" + dbUri.getPort() + dbUri.getPath());
        	    configuration.setProperty("hibernate.connection.username", dbUri.getUserInfo().split(":")[0]);
        	    configuration.setProperty("hibernate.connection.password", dbUri.getUserInfo().split(":")[1]);
        	    configuration.setProperty("hibernate.show_sql", "true");
        	 // configuration.setProperty("hibernate.default_schema", "reqman");
        	    configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        	    configuration.addAnnotatedClass(Category.class);
        	    configuration.addAnnotatedClass(Project.class);
        	    configuration.addAnnotatedClass(Users.class);
                ServiceRegistry serviceRegistry
                    = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                 
                // builds a session factory from the service registry
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);           
            }

    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
        return sessionFactory;
    }
    
    public static Session getSession()
    {
    	session = HibernateUtilH.getSessionFactory() != null 
    			? HibernateUtilH.getSessionFactory().openSession() : null;
    			
		return session;
    }
    
    


}