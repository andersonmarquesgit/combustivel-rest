package com.combustivel.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.combustivel.api.entity.Product;
import com.combustivel.api.repository.ProductRepository;
import com.combustivel.api.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public void createProducts(List<Product> products) {
		for (Product product : products) {
			this.productRepository.save(product);
		}
	}
	
	@Override
	public List<Product> findAllByRegion(String region) {
		return productRepository.findAllByRegion(region);
	}
}
