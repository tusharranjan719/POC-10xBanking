package com.application.demo.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.lang.Nullable;

@Entity 
public class Product {
	
	@Id
	private long product_key;

	private String product_name;
	private String product_version;
	
	@ManyToOne
	//@JoinColumn(name= "customerId")
	private Customer customer;
	
	@Nullable
	private UUID subscriptionId;
	
	
	
	public UUID getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(UUID subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
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
	@Override
	public String toString() {
		return "Product [product_key=" + product_key + ", product_name=" + product_name + ", customer=" + customer
				+ "]";
	}

	
	

	
}
