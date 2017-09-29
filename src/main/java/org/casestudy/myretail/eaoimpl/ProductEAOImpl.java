package org.casestudy.myretail.eaoimpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.casestudy.myretail.persistance.Product;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

//This class establishes connection with database and performs necessary actions.
public class ProductEAOImpl {

//HQL constants	
public static final String getProductsByIDHQL = "from Product where id in (:productId)";
public static final String getProductsByCategoryHQL = "from Product where category in (:category)";
public static final String getAllProductsByHQL = "from Product as p";

//Instance of Hibernate Session Factory
SessionFactory sessionFactory;
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

/**
 * Constructor
 * @param sessionFactory
 */
public ProductEAOImpl(SessionFactory sessionFactory)
{
	this.sessionFactory = sessionFactory;
}

/**
 * Retrieves all the products from the database
 * @return
 */
@SuppressWarnings("unchecked")
public List<Product> getAllProducts(){
	Session session = null;
	List<Product> allProducts = null;
	try{
		//open a hibernate session
		session=sessionFactory.openSession();
		//execute the HQL to get all products from the database
		allProducts = session.createQuery(getAllProductsByHQL).list();
	}
	finally{
		closeSession(session);
	}
	//returns list of all products
		return allProducts;
}
/**
 * Fetches single or multiple products from the database
 * @param productsList
 * @return
 */
@SuppressWarnings("unchecked")
public List<Product> getProduct(List<Integer> productsList){
	
	Session session = null;
	List<Product> result = null;
	//Creates the hibernate session
	try{
		//open session
		session=sessionFactory.openSession();
		//get all the products using productsList 
		result = session.createQuery(getProductsByIDHQL).setParameterList("productId", productsList).list();
	}
	finally{
		closeSession(session);	
	}
	//returns list of single/multiple products
	return result;
	
}
/**
 * Fetches the product using productID
 * @param productId
 * @return
 */
public Product getProduct(int productId)
{

	Session session = null;
	Object result = null;
	try
	{
		//creates the hibernate session
		session = sessionFactory.openSession();
		//set criteria to look for a product with unique id
		Criteria criteria = session.createCriteria(Product.class).add(Restrictions.eq("id", productId));
		result = criteria.uniqueResult();
	}
	finally
	{
		closeSession(session);
	}
	//returns single product
	return (Product)result;

}
/**
 * Adds new product to the database.
 * @param product
 * @return
 * @throws Exception
 */
public Product addProduct(Product product) throws Exception{
	
	Session session = null;
	Transaction tx = null;
	try{
		//create a hibernate session
		session=sessionFactory.openSession();
		//create the transaction
		tx = session.beginTransaction();
		//update the product with new updated date
		product.setLast_udpated(getLastUpdatedDate());
		//persist the new product to database
		session.persist(product);
		session.flush();
		tx.commit();
		
	}
	catch(HibernateException e){
		//log error details
		//incase of any exception rollback the transaction
		tx.rollback();
		throw new Exception (e.getMessage());
	}
	finally{
		closeSession(session);
		
		}
	//returns the newly added product
	return product;	
}
/**
 * Update the existing product in the databse
 * @param product
 * @return
 */
public Product updateProduct(Product product){
	Session session = null;
	Transaction tx = null;
	try{
		//creates the hibernate session
		session=sessionFactory.openSession();
		//create the session
		tx = session.beginTransaction();
		//update the product with new updated date
		product.setLast_udpated(getLastUpdatedDate());
		//update the record
		session.saveOrUpdate(product);
		session.flush();
		tx.commit();
	}catch(HibernateException e){
		//log error details
		//in case of any exception rollback the transaction
		tx.rollback();
	}
	finally{
		closeSession(session);
	}
	//returns the updated product
	return product;	
}

/**
 * Delete the product from the database
 * @param product
 * @return
 */
public boolean deleteProduct(Product product){
	
	Session session = null;
	Transaction tx = null;
	boolean isDeleted = false;
	
	try{
		//creates the hibernate session
		session=sessionFactory.openSession();
		//create the transaction
		tx = session.beginTransaction();
		//delete the product and commit
		session.delete(product);
		session.flush();
		tx.commit();
		isDeleted = true;
	}
	catch(HibernateException e){
		//log error details
		//in case of any exception rollback the transaction
		tx.rollback();
	}
	finally{
		closeSession(session);
	
	}
	//return boolean value true or false depending on transaction
	return isDeleted;	
}

/**
 * Fetches the products by single or multiple categories
 * @param categoryList
 * @return
 */
@SuppressWarnings("unchecked")
public List<Product> getProductsByCategory(List<String> categoryList){
	
	Session session = null;
	List<Product> result = null;
	
	try{
		//Creates the hibernate session
		session=sessionFactory.openSession();
		//gets the list of categories from the database
		result = session.createQuery(getProductsByCategoryHQL).setParameterList("category", categoryList).list();
	}
	finally{
		
		closeSession(session);
	}
	//returns the list of products by categories
	return result;
	
}

/**
 * Invoked this method to close the open session
 * @param session
 */
private void closeSession(Session session){

	if(session != null){
		session.close();
	}
}
/**
 * get the current date
 * @return
 */
public String getLastUpdatedDate(){
	return sdf.format(new Date());
}

}
