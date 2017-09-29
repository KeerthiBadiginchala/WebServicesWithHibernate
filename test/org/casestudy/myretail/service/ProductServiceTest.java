package org.casestudy.myretail.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import junit.framework.Assert;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.casestudy.myretail.persistance.Product;
import org.casestudy.myretail.resource.ProductResource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
public class ProductServiceTest
{
	private static Client clientObj = null;
	private static DefaultHttpClient httpClient = null;
	Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
	private static final String productsResourceURI = "http://localhost:8080/myRetail/webapi/products/";
	private static final String getProductResourceURI = "http://localhost:8080/myRetail/webapi/products/5560";
	
	/**
	 * Executed first before running test cases
	 */
	@BeforeClass
	public static void before(){
		//creates client object
		clientObj = Client.create();
		//creates the httpClient object
		httpClient = new DefaultHttpClient();
		
	}
	/**
	 * Test getAllProducts() on {@link ProductResource}
	 * SUCCESS Test Case
	 */
	@Test
	public void allProductsServiceTest(){
		ClientResponse clientResponse = getClientResponse(productsResourceURI);
		//check for response code, 200 is success
		Assert.assertEquals(200, clientResponse.getStatus());
		//get the data
		String json = clientResponse.getEntity(String.class);
		//convert the jsonarray to product array using gson
		Product[] product = gson.fromJson(json, Product[].class);
		//check for null
		Assert.assertNotNull(product);
		//check for length
		Assert.assertTrue(product.length > 0);
	}
	
	/**
	 * Test getProduct(@PathParam("productId")String productId) on {@link ProductResource}
	 * SUCCESS Test Case
	 */
	@Test
	public void getProductsServiceTest(){
		ClientResponse clientResponse = getClientResponse(getProductResourceURI);
		//check for response code, 200 is success
		Assert.assertEquals(200, clientResponse.getStatus());
		//get the data
		String json = clientResponse.getEntity(String.class);
		//convert the jsonarray to product array using gson
		Product[] product = gson.fromJson(json, Product[].class);
		//check for null
		Assert.assertNotNull(product);
		//check for length
		Assert.assertEquals(1, product.length);;
	}
	/**
	 * Test String addProduct(Product product) on {@link ProductResource}
	 * SUCCESS Test case
	 */
	@Test
	public void postProductServiceTest(){
		
		
		//creating HttpPost request object
		HttpPost postRequest = new HttpPost(productsResourceURI);
		StringEntity input;
		try {
			//create a stringEntity with JSON format
			input = new StringEntity("{\"id\": 2222, \"sku\": \"AEX1234\", \"name\": \"Stroller\",\"category\": \"baby\",\"productPrice\": {\"id\": 2222,\"price\": 9999.99}}");
		//set content type
		input.setContentType("application/json");
		//send the post request with the message
		postRequest.setEntity(input);
		//fetch the response
		HttpResponse response = httpClient.execute(postRequest);
		//Check if response status code is 201 for POST request
		Assert.assertEquals(201, response.getStatusLine().getStatusCode());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
	}
	
	/**
	 * Returns the ClientResponse based on the Resource URI
	 * @param serviceURI
	 * @return
	 */
	public ClientResponse getClientResponse(String serviceURI){
		//get the webresource
		WebResource webResource = clientObj.resource(serviceURI);
		//get the client response with JSON format
		ClientResponse response = webResource.accept("application/json")
		                   .get(ClientResponse.class);
		return response;
		 
	}
	
	@AfterClass
	public static void after(){
		
		//shutdown the httpclient object
		httpClient.getConnectionManager().shutdown();
		//destroy the client object
		clientObj.destroy();
		
	}
	
	
	
}
