package com.application.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.application.demo.dao.CustProdRepo;
import com.application.demo.model.Customer;

@Controller
public class CustProdController {
	@Autowired
	CustProdRepo repo;

	@RequestMapping("/")
	public String home() {
		return "home.jsp";
	}

	@RequestMapping("/addCustomer")
	public String addCustomer(Customer customer) {
		repo.save(customer);
		return "home.jsp";
	}
}
