package org.casestudy.myretail.resource;

import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.casestudy.myretail.service.ProductService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/categories")
public class CategoryResource {
	
	ProductService productService = new ProductService();
	Properties prop = new Properties();
	InputStream input = null;
	Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
	
	@GET
	@Path("/{categoryType}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getProductsByCategory(@PathParam("categoryType")String categoryType){
		System.out.println("++++++++++++ IN GET PRODUCT(S) +++++++++++++++");
		return gson.toJson(productService.getProductsByCategory(categoryType));
		
	}

}
