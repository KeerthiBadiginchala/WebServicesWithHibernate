package org.casestudy.myretail.persistance;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;



@Entity
@Table(name="product_details")
@XmlRootElement
public class Product {
	@Column(name="id")
	@Id
	@Expose
	private int id;
	@Column(name="sku")
	@Expose
	private String sku;
	@Column(name="name")
	@Expose
	private String name;
	@Column(name="category")
	@Expose
	private String category;
	@Column(name="last_updated")
	@Expose
	private String last_udpated;
	@OneToOne(mappedBy="product", cascade=CascadeType.ALL)
	@Expose
	private ProductPrice productPrice;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getLast_udpated() {
		return last_udpated;
	}
	public void setLast_udpated(String last_udpated) {
		this.last_udpated = last_udpated;
	}
	public ProductPrice getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(ProductPrice productPrice) {
		this.productPrice = productPrice;
	}

}
