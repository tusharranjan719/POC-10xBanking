package com.application.demo.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.application.demo.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long>{
	
	@Query("from Product where product_name=?1")
	Product findByProdName(String product_name);

}
