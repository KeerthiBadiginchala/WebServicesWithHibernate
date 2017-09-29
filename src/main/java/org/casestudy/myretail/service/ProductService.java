package org.casestudy.myretail.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.casestudy.myretail.eaoimpl.ProductEAOImpl;
import org.casestudy.myretail.persistance.Product;
import org.casestudy.myretail.util.HibernateUtil;

//This class gets the request from the ProductResource and handles the business logic if any.
public class ProductService {
	
	//creating instance of Entity Access Object 
	private ProductEAOImpl productEAO;
	//RegEx to validate the productID 
	private static Pattern p = Pattern.compile("[0-9]+(,[0-9]+)*");
	/**
	 * Default constructor
	 */
	public ProductService()
	{
		this.productEAO = new ProductEAOImpl(HibernateUtil.getSessionFactory());
	}
	
	/**
	 * Returns all products
	 * @return
	 */
	public List<Product> getAllProducts(){
		return productEAO.getAllProducts();
	}
	
	/**
	 * Returns a single product details
	 * @param id
	 * @return
	 */
	public Product getProduct(int id)
	{
		return productEAO.getProduct(id);
	}
	
	/**
	 * Returns single/multiple products 
	 * @param productIDs
	 * @return
	 * @throws Exception 
	 */
	public List<Product> getProduct(String productIDs) throws Exception{
		
		List<Integer> productsList = new ArrayList<Integer>();
		//if multiple product id's are passed in the url, splits the string with ',' as delimiter and stores in an array
		if(productIDs != null && productIDs.trim() != " "){
			try{
				String[] productArray = validateProductID(productIDs);
				for(int i=0;i<productArray.length;i++){
					productsList.add(Integer.parseInt(productArray[i]));
				}
			}catch(IllegalArgumentException e){
				throw new Exception(e.getMessage());
				
			}
		}
		
		//converts the each productid from string to integer and adds to integer arraylist
		
		return productEAO.getProduct(productsList);
	}
	/**
	 * Updates the product with new details and returns the product
	 * @param product
	 * @return
	 */
	public Product updateProduct(Product product){
		return productEAO.updateProduct(product);
		
	}
	/**
	 * Adds new product and returns the new product details
	 * @param product
	 * @return
	 * @throws Exception 
	 */
	public Product addProduct(Product product) throws Exception{
		return productEAO.addProduct(product);
		
	}	
	/**
	 * Deletes the product.
	 * @param productId
	 * @return
	 */
	public boolean deleteProduct(int productId){
		//Fetches the product object using the id 
		return productEAO.deleteProduct(this.getProduct(productId));
		
	}
	/**
	 * Returns single/multiple products based on the category
	 * @param categoryTypes
	 * @return
	 */
	public List<Product> getProductsByCategory(String categoryTypes){
		//if multiple categories are passed in the url, split it and add them to a string array
		String[] categoryArray = categoryTypes.split(",");
		List<String> categoryList = new ArrayList<String>();
		for(int i=0;i<categoryArray.length;i++){
			categoryList.add(categoryArray[i]);
		}
		return productEAO.getProductsByCategory(categoryList);
	}
	
	private String[] validateProductID(String productID){
		 String[] values = null;    
	     Matcher m = p.matcher(productID);
	     if(m.matches()){
	        values = productID.split(",");
	     }
	     else{
	        throw new IllegalArgumentException("Improper product id(s) format. Valid formats are 5555 or 5555,6666");
	}
	     return values;
	}

}
