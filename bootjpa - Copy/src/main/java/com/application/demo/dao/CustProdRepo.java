package com.application.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.application.demo.model.Customer;

public interface CustProdRepo extends CrudRepository<Customer, Integer> {

}
