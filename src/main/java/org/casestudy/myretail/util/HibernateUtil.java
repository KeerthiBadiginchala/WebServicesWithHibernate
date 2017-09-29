package org.casestudy.myretail.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//Singleton class which returns only one instance of sessionFactory
public class HibernateUtil {
 
	private static SessionFactory sessionFactory = null;
	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			return new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			// Caught exceptions if there are any configuration errors.
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
 /**
  * Returns SessionFactory
  * @return
  */
	public static SessionFactory getSessionFactory() {
		//Check for sessionFactory object, if null create the sessionFactory object, if not return open sessionFactory.
		if(sessionFactory == null){
			sessionFactory = buildSessionFactory();
		}
		return sessionFactory;
			
	}
 /**
  * To close the sessionFactory
  */
	public void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
 
}