package com.application.demo.controller;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.demo.dao.CustProdRepo;
import com.application.demo.model.Customer;

@RestController
public class CustProdController {
	@Autowired
	CustProdRepo repo;

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
	public UUID addCustomer(@RequestBody Customer customer)
	{
		repo.save(customer);
		return customer.getCustomerId();
	}
	
	@RequestMapping("/CustomerId/{customerId}")
	public Optional<Customer> getCustomer(@PathVariable("customerId")UUID customerId)
	{
		return repo.findById(customerId);
		
	}
	
	@RequestMapping("/CustomerName/{first_name}")
	public List<Customer> getCustomerbyName(@PathVariable("first_name")String first_name)
	{
		return repo.findByName(first_name);
			
	}
	
	@DeleteMapping("/customer/{customerId}")
	public String deleteCusomer(@PathVariable UUID customerId)
	{
		Customer c = repo.getOne(customerId);
		String name=c.getFirst_name();
		repo.delete(c);
		return "deleted user- "+name;
	}
	
	@PutMapping(path="/customer",consumes= {"application/json"})
	public Customer UpdateCustomer(@RequestBody Customer customer)
	{	
		Customer c = repo.getOne(customer.getCustomerId());
		c.setAddress(customer.getAddress());
		repo.save(c);
		return c;
	}
	
	
	
}
