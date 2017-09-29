package org.casestudy.myretail.persistance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="product_price")
@XmlRootElement
public class ProductPrice {
	
	@Column(name="id")
	@Id
	@GenericGenerator(name="gen", strategy="foreign", parameters={@Parameter(name="property", value="product")})
	public int id;
	@Column(name="price")
	@Expose
	public double price;
	@OneToOne
	@JoinColumn(name = "id")
	@PrimaryKeyJoinColumn
	private  Product product ;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

}
