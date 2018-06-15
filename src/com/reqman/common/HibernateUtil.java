package com.reqman.common;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.reqman.pojo.Account;
import com.reqman.pojo.Accountusers;
import com.reqman.pojo.Audittrail;
import com.reqman.pojo.Category;
import com.reqman.pojo.Customerpayment;
import com.reqman.pojo.Menu;
import com.reqman.pojo.Organisationdetails;
import com.reqman.pojo.Project;
import com.reqman.pojo.Publicemaildomains;
import com.reqman.pojo.Request;
import com.reqman.pojo.Requestnotes;
import com.reqman.pojo.Requesttype;
import com.reqman.pojo.Rolemenus;
import com.reqman.pojo.RolemenusId;
import com.reqman.pojo.Roles;
import com.reqman.pojo.Schdulejobs;
import com.reqman.pojo.Suggestion;
import com.reqman.pojo.Usercategory;
import com.reqman.pojo.Userfriendlist;
import com.reqman.pojo.Userproject;
import com.reqman.pojo.Userrequesttype;
import com.reqman.pojo.Userroles;
import com.reqman.pojo.UserrolesId;
import com.reqman.pojo.Users;
import com.reqman.pojo.Usertype;
import com.reqman.pojo.Userusertype;
import com.reqman.pojo.UserusertypeId;
 
public class HibernateUtil {
    private static SessionFactory sessionFactory;
	private static Session session;
	private static Configuration configuration;
	public static String schemaName="reqman";
	static {

    	try{
        if (sessionFactory == null) {
            // loads configuration and mappings
            configuration = new Configuration();
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
    	    configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
    	    configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/raqman4");
    	    configuration.setProperty("hibernate.connection.username", "postgres");
    	    configuration.setProperty("hibernate.connection.password", "3394");
    	    configuration.setProperty("hibernate.show_sql", "true");
    	    configuration.setProperty("hibernate.default_schema", "reqman");
    	    
    	   configuration.setProperty("hibernate.hbm2ddl.auto", "update");
    	    
    	    configuration.addAnnotatedClass(Audittrail.class);
    	    configuration.addAnnotatedClass(Category.class);
    	    configuration.addAnnotatedClass(Menu.class);
    	    configuration.addAnnotatedClass(Project.class);
    	    configuration.addAnnotatedClass(Request.class);
    	    configuration.addAnnotatedClass(Requesttype.class);
    	    configuration.addAnnotatedClass(Accountusers.class);
    	    configuration.addAnnotatedClass(Rolemenus.class);
    	    configuration.addAnnotatedClass(RolemenusId.class);
    	    configuration.addAnnotatedClass(Roles.class);
    	    configuration.addAnnotatedClass(Usercategory.class);
    	    configuration.addAnnotatedClass(Userfriendlist.class);
    	    configuration.addAnnotatedClass(Userproject.class);
    	    configuration.addAnnotatedClass(Userrequesttype.class);
    	    configuration.addAnnotatedClass(Userroles.class);
    	    configuration.addAnnotatedClass(UserrolesId.class);
    	    configuration.addAnnotatedClass(Users.class);
    	    configuration.addAnnotatedClass(Usertype.class);
    	    configuration.addAnnotatedClass(Userusertype.class);
    	    configuration.addAnnotatedClass(UserusertypeId.class);
    	    configuration.addAnnotatedClass(Suggestion.class);
    	    configuration.addAnnotatedClass(Requestnotes.class);
    	    configuration.addAnnotatedClass(Account.class);
    	    configuration.addAnnotatedClass(Customerpayment.class);
    	    configuration.addAnnotatedClass(Organisationdetails.class);
    	    configuration.addAnnotatedClass(Publicemaildomains.class);
    	    configuration.addAnnotatedClass(Schdulejobs.class);
            ServiceRegistry serviceRegistry
                = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
             
            // builds a session factory from the service registry
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);           
        }


	
}catch(Exception ex){
	ex.printStackTrace();
}
      
    
	}
    public static SessionFactory getSessionFactory() {
    	  return sessionFactory;
    }
    
    public static Session getSession()
    {
    	session = HibernateUtil.getSessionFactory() != null 
    			? HibernateUtil.getSessionFactory().openSession() : null;
    			
		return session;
    }

	public static Configuration getConfiguration() {
		return configuration;
	}

    
    


}