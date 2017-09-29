package org.casestudy.myretail.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.casestudy.myretail.persistance.Product;
import org.casestudy.myretail.service.ProductService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//When looking up for resource "products", servlet container matches this class based on url pattern
@Path("/products")
public class ProductResource {
	
	//instantiating the ProductService Class
	ProductService productService = new ProductService();
	//using gson open source library to convert complex java objects to json, with readable format and excluding the
	//properties which are not annotated with @expose annotation
	Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
	
	/**
	 * Fetches all products in JSON format
	 * url pattern: http://localhost:8080/myRetail/webapi/products/
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllProducts(){
		List<Product> productList = productService.getAllProducts();
		return gson.toJson(productList);
	}
	
	/**
	 * Fetches single/multiple products based on product ID in JSON format.
	 * url pattern: http://localhost:8080/myRetail/webapi/products/5555 (returns single product)
	 * or
	 * url pattern: http://localhost:8080/myRetail/webapi/products/5555,5556 (returns two products)
	 * @param productId
	 * @return
	 */
	@GET
	@Path("/{productId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getProduct(@PathParam("productId")String productId){
		try {
			List<Product> productsList  = productService.getProduct(productId);
			if(productsList != null && productsList.size() > 0){
				return gson.toJson(productsList);
			}
			else{
				return "No Product found with ID: "+ productId;
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return e.getMessage();
		}
		
		
	}
	
	/**
	 * Creates a new product. Consumes and Produces JSON format.
	 * url pattern: http://localhost:8080/myRetail/webapi/products/
	 * @param product
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addProduct(Product product){
		try {
			System.out.println("******************");
			return gson.toJson(productService.addProduct(product));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error while adding new product, please refer logs.";
		}
	}
	
	/**
	 * Updates the product details. Consumes and Products in JSON format
	 * url pattern: http://localhost:8080/myRetail/webapi/products/5555
	 * @param productId
	 * @param product
	 * @return
	 */
	@PUT
	@Path("/{productId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateProduct(@PathParam("productId")int productId,Product product){
		product.setId(productId);
		product.getProductPrice().setId(productId);
		return gson.toJson(productService.updateProduct(product));
	}
	
	/**
	 * Delete the product from the database
	 * url pattern: http://localhost:8080/myRetail/webapi/products/5555
	 * @param productId
	 * @return
	 */
	@DELETE
	@Path("/{productId}")
	public String deleteProduct(@PathParam("productId")int productId){
		boolean isDeleted = productService.deleteProduct(productId);
		if(!isDeleted){
			return "No Product exist with ID:"+productId+". Please enter a valid ID.";
		}
		else{
			return "Product "+productId+" is successfully deleted.";
		}
		
	}

}
