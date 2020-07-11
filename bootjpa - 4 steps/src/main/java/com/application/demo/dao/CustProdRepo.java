package com.application.demo.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.application.demo.model.Customer;

public interface CustProdRepo extends JpaRepository<Customer, UUID> {

	@Query("from Customer where first_name=?1")
	List<Customer> findByName(String first_name);
}
