package com.application.demo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;


@Entity
public class Customer {
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private UUID customerId;
	
//    @Pattern(regexp="/^([a-zA-Z ]){2,30}$/",message="invalid first name")  
	private String first_name;
 //   @Pattern(regexp="/^([a-zA-Z ]){2,30}$/",message="invalid last name")
	private String last_name;
  //  @Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$",message="enter YYYY-MM-DD")
	private Date dob;
 //   @Pattern(regexp="^(?:m|M|male|Male|f|F|female|Female)$",message="M/F/Male/Female only")
    private String gender;
    
	private String address;

	
	@OneToMany(
		//	mappedBy = "customer",
			cascade = CascadeType.ALL
			)
	@JoinColumn(name="customerId")
	private List<Product> products=new ArrayList<>();
	
	

	
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	
	public UUID getCustomerId() {
		return customerId;
	}
	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Customer [id=" + customerId + ", name=" + first_name + "]";
	}

}
