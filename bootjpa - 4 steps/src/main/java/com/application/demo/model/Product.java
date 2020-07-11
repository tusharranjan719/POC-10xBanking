package com.application.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity 
public class Product {
	
	@Id
	private long product_key;

	private String product_name;
	private String product_version;
	
	public String getProduct_version() {
		return product_version;
	}
	public void setProduct_version(String product_version) {
		this.product_version = product_version;
	}
	public long getProduct_key() {
		return product_key;
	}
	public void setProduct_key(long product_key) {
		this.product_key = product_key;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

}
