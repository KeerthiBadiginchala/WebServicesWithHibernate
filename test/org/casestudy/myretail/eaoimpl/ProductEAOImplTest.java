package org.casestudy.myretail.eaoimpl;

import java.util.ArrayList;
import java.util.List;

import org.casestudy.myretail.eaoimpl.ProductEAOImpl;
import org.casestudy.myretail.persistance.Product;
import org.casestudy.myretail.persistance.ProductPrice;
import org.casestudy.myretail.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This class performs the test cases for all the database operations
 * Total Test Cases: 9
 * Success: 6
 * Failure: 3
 * @author saichinnappa
 *
 */
public class ProductEAOImplTest
{

	public static SessionFactory sessionFactory;
	public static ProductEAOImpl productRepository;
	//ThisProduct ID is used for update test cases
	public static final int updateTestCaseProductID = 1111;
	//This Product ID is used for adding and deleting test cases
	public static final int addAndDeleteTestCaseProductID = 9999;
	/**
	 * This method is executed before all test cases
	 * @throws Exception 
	 */
	@BeforeClass
	public static void before() throws Exception
	{
		sessionFactory = HibernateUtil.getSessionFactory();
		productRepository = new ProductEAOImpl(sessionFactory);
		//inserting a test product to the database for test cases, this record will be later deleted
		//in the @AfterClass method after execution of all test cases.
		addProduct(updateTestCaseProductID);
	
	}

	/**
	 * Test getAllProducts() on {@link ProductEAOImpl}
	 * SUCCESS test case
	 */
	@Test
	public void getAllProductsTest()
	{
		List<Product> allProducts = productRepository.getAllProducts();
		//check for null
		Assert.assertNotNull(allProducts);
		Assert.assertTrue(allProducts.size() > 0);

	}

	/**
	 * Test getProduct(int) on {@link ProductEAOImpl}
	 * SUCCESS test case
	 */
	@Test
	public void getProductsTestWithValidProductID()
	{
		Product product = productRepository.getProduct(updateTestCaseProductID);
		//check for null
		Assert.assertNotNull(product);
		//check for id equality 
		Assert.assertEquals(updateTestCaseProductID, product.getId());

	}
	
	/**
	 * Test getProduct(int) on {@link ProductEAOImpl}
	 * FAILURE test case
	 */
	@Test
	public void getProductsTestWithInvalidProductID()
	{
		Product product = productRepository.getProduct(5563);
		//check for null
		Assert.assertNull(product);
		

	}

	/**
	 * Test getProduct(List<Integer>) on {@link ProductEAOImpl}, check for multiple products retrieval
	 * SUCCESS test case
	 */
	@Test
	public void getProductsForListTest()
	{
		List<Integer> productIds = new ArrayList<Integer>();
		productIds.add(1111);
		productIds.add(2222);
		List<Product> products = productRepository.getProduct(productIds);
		Assert.assertNotNull(products);
		Assert.assertTrue(products.size() > 0);

	}

	/**
	 * Test addProduct(Product) on {@link ProductEAOImpl}
	 * SUCCESS test case
	 * @throws Exception 
	 */
	@Test
	public void addProductTest() throws Exception
	{
		addProduct(addAndDeleteTestCaseProductID);
		Product product = productRepository.getProduct(addAndDeleteTestCaseProductID);
		//check for null
		Assert.assertNotNull(product);
		//check for id equality
		Assert.assertEquals(addAndDeleteTestCaseProductID, product.getId());

	}

	/**
	 * Test deleteProduct(int) on {@link ProductEAOImpl}
	 * SUCCESS test case
	 * @throws Exception 
	 */
	@Test
	public void deleteProductTest() throws Exception
	{
		Product product = productRepository.getProduct(addAndDeleteTestCaseProductID);
		//check for null
		Assert.assertNotNull(product);
		//check for id equality
		Assert.assertEquals(addAndDeleteTestCaseProductID, product.getId());
		//delete the product
		productRepository.deleteProduct(product);
		//Fetch the product
		Product deletedproduct = productRepository.getProduct(addAndDeleteTestCaseProductID);
		//check for null
		Assert.assertNull(deletedproduct);

	}

	/**
	 * Test updateProduct(Product) on {@link ProductEAOImpl}
	 * @throws Exception 
	 */
	@Test
	public void updateProductTest() throws Exception
	{
		//get the product
		Product product = productRepository.getProduct(updateTestCaseProductID);
		//check for null
		Assert.assertNotNull(product);
		//check for id equality
		Assert.assertEquals(updateTestCaseProductID, product.getId());
		//update the values
		product.setSku("SKU Updated");
		product.setCategory("Toy Updated");
		//invoke the method
		productRepository.updateProduct(product);
		//get the product
		Product updatedProduct = productRepository.getProduct(updateTestCaseProductID);
		//check for null
		Assert.assertNotNull(updatedProduct);
		//check for id equality
		Assert.assertEquals(updateTestCaseProductID, updatedProduct.getId());
		//check for updated values
		Assert.assertEquals("SKU Updated", updatedProduct.getSku());
		Assert.assertEquals("Toy Updated", updatedProduct.getCategory());

	}

	/**
	 * Test getProductsByCategory(List<String>) on {@link ProductEAOImpl}
	 * SUCCESS test case
	 * @throws Exception 
	 */
	@Test
	public void getProductsWithValidCategoryTest() throws Exception
	{
		//add the category
		List<String> categoryLst = new ArrayList<String>();
		categoryLst.add("Toy Updated");
		//fetches the records 
		List<Product> products = productRepository.getProductsByCategory(categoryLst);
		//check for null
		Assert.assertNotNull(products);
		//check for size
		Assert.assertEquals(1, products.size());

	}
	
	/**
	 * Test getProductsByCategory(List<String>) on {@link ProductEAOImpl}
	 * FAILURE test case
	 * @throws Exception 
	 */
	@Test
	public void getProductsWithInvalidCategoryTest() throws Exception
	{
		//add the category
		List<String> categoryLst = new ArrayList<String>();
		categoryLst.add("Toy XYZ");
		//fetches the records 
		List<Product> products = productRepository.getProductsByCategory(categoryLst);
		//check for null
		Assert.assertTrue(products.size()==0);
		

	}
	
	/**
	 * inserting a test product to the database to test update test cases
	 * @param id
	 * @throws Exception 
	 */
	public static void addProduct(int id) throws Exception
	{
		//creating the ProductPrice object
		ProductPrice price = new ProductPrice();
		price.setId(id);
		price.setPrice(3.01);

		//Creating the Product object
		Product product = new Product();
		product.setId(id);
		product.setSku("Test Product");
		product.setCategory("Test Toy");
		product.setName("Test Product");
		product.setProductPrice(price);
		//inserting into the database
		productRepository.addProduct(product);

	}
	
	/**
	 * deleting the test product
	 * @param id
	 * @throws Exception 
	 */
	public static void deleteProduct(int productID) throws Exception
	{
		//Get the product object
		Product product = productRepository.getProduct(productID);
		//delete the product
		productRepository.deleteProduct(product);

	}

	/**
	 * This is executed after all the @Test cases
	 * @throws Exception 
	 */
	@AfterClass
	public static void afterTest() throws Exception
	{
		sessionFactory.close();
		deleteProduct(updateTestCaseProductID);
	}

}
