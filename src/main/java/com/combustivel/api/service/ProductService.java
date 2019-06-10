package com.combustivel.api.service;

import java.util.List;

import com.combustivel.api.entity.Product;

public interface ProductService {

	void createProducts(List<Product> products);

	List<Product> findAllByRegion(String region);
}
