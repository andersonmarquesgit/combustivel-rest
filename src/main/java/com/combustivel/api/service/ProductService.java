package com.combustivel.api.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import com.combustivel.api.entity.Product;

public interface ProductService {

	void createProducts(List<Product> products);

	List<Product> findAllByRegion(String region);
	
	List<Product> findAllByResale(String resaleDesc);
	
	List<Product> findByDtCollect(LocalDate dtCollect);

	Product createOrUpdate(Product product);
	
	Product findById(String id);
	
	void delete(String id);
	
	Page<Product> findAll(int page, int count);
}
