package com.application.demo.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.demo.dao.CustProdRepo;
import com.application.demo.dao.ProductRepo;
import com.application.demo.exceptions.CustomerNotFoundException;
import com.application.demo.exceptions.ProductAlreadyInUseException;
import com.application.demo.model.Customer;
import com.application.demo.model.Product;

@RestController
public class CustProdController {
	@Autowired
	CustProdRepo repo;
	
	@Autowired
	ProductRepo prod;

//	@RequestMapping("/")
//	public String home() {
//		return "home.jsp";
//	}

//	@RequestMapping("/addCustomer")
//	public String addCustomer(Customer customer) {
//		repo.save(customer);
//		return "home.jsp";
//	}
	
	
	@PostMapping(path="/addCustomer",consumes= {"application/json"})
	public UUID addCustomer(@Valid @RequestBody Customer customer)
	{
		repo.save(customer);
		return customer.getCustomerId();
	}
	
	@RequestMapping("/CustomerId/{customerId}")
	public Customer getCustomer(@PathVariable("customerId")UUID customerId)
			throws CustomerNotFoundException
	{
		Customer customer= repo.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("User not found on :: "+ customerId));
		return customer;
		
	}
	
	@RequestMapping("/CustomerName/{first_name}")
	public List<Customer> getCustomerbyName(@PathVariable("first_name")String first_name)
			throws CustomerNotFoundException
	{
		List<Customer> customers= repo.findByName(first_name);
		if(customers.isEmpty()) {throw new CustomerNotFoundException("No user found with name :: "+ first_name);}
		return customers;
	}
	
	@DeleteMapping("/customer/{customerId}")
	public String deleteCusomer(@PathVariable UUID customerId) throws CustomerNotFoundException
	{
		Customer c = repo.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("User not found on :: "+ customerId));
		String name=c.getFirst_name();
		repo.delete(c);
		return "deleted user- "+name;
	}
	
	@PutMapping(path="/customer",consumes= {"application/json"})
	public Customer UpdateCustomer(@Valid @RequestBody Customer customer) throws CustomerNotFoundException
	{	

		Customer c = repo.findById(customer.getCustomerId()).orElseThrow(()-> new CustomerNotFoundException("User not found on :: "+ customer.getCustomerId()));
		c.setAddress(customer.getAddress());
		repo.save(c);
		return c;
	}
	
	//product add & remove:
	
	@PostMapping(path="/addProduct/{customerId}",consumes= {"application/json"})
	public HashMap<String,UUID> addProduct(@PathVariable(value = "customerId") UUID customerId, 
			@Valid @RequestBody List<String> product_name) 
	{	
		
		customerCheck(customerId);
		productCheck(product_name);
		availabiltyCheck(product_name);
		
		Customer c=repo.getOne(customerId);

		
		List<Product> listofProducts=new ArrayList<>();
		HashMap<String,UUID> response= new HashMap<>();
		
		for(String s:product_name)
		{
		Product p= prod.findByProdName(s);		
		p.setSubscriptionId(UUID.randomUUID());
		response.put(s, p.getSubscriptionId());
		listofProducts.add(p);
		p.setCustomer(c);
		prod.save(p);
		}
		
		c.setProducts(listofProducts);
		
		repo.save(c);
		
		return response;
		
	}
	
	@PutMapping(path="/removeProduct/{customerId}",consumes= {"application/json"})
	public String removeProduct(@PathVariable(value = "customerId") UUID customerId, 
			@Valid @RequestBody List<String> product_name) 
	{
		
		customerCheck(customerId);
		productCheck(product_name);
		
		for(String s:product_name)
		{
		Product p= prod.findByProdName(s);
		if(p.getCustomer()==null) {
			throw new CustomerNotFoundException("The product "+s+" is not owned by you, but is available. Please add first to remove");
		}
		if(p.getCustomer().getCustomerId() !=customerId )
		{
			throw new CustomerNotFoundException("Product "+s+" doesn't belong to you");
		}
			p.getCustomer().setCustomerId(null);
		p.setSubscriptionId(null);
		}
		
		return "removed";
	
	}
	
	
	//check:
	
	public void customerCheck(UUID customerId) {
		
		Optional<Customer> check=repo.findById(customerId);
		if(check.isEmpty())
		{
			throw new CustomerNotFoundException("User not found on :: "+ customerId);
		}
		
	}
	
	public void productCheck(List<String> product_name)
	{
		
		for(String i:product_name) {
			Product p=prod.findByProdName(i);
			if(p==null) 
				{
				throw new ProductAlreadyInUseException("The product "+i+" doesn't exist");
				}
		}
	}
		
	public void availabiltyCheck(List<String> product_name)
	{
		for(String i:product_name) {
			
			Product p=prod.findByProdName(i);
			if(p.getSubscriptionId()!=null) {
				throw new ProductAlreadyInUseException("The product "+i+" is not available, please alter your choices");
			}
		
		}
		
	}
	
	
	//handling exceptions:
	
	@ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerExceptions(CustomerNotFoundException e) {
       
        return new ResponseEntity<>("ERROR: " + e.getMessage(),HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(ProductAlreadyInUseException.class)
    public ResponseEntity<String> handleProductExceptions(ProductAlreadyInUseException e) {
       
        return new ResponseEntity<>("OOPS!: " + e.getMessage(),HttpStatus.BAD_REQUEST);
    }
	
	
}
