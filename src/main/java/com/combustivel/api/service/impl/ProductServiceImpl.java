package com.combustivel.api.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

	@Override
	public List<Product> findAllByResale(String resaleDesc) {
		return productRepository.findAllByResale(resaleDesc);
	}

	@Override
	public List<Product> findByDtCollect(LocalDate dtCollect) {
		return productRepository.findByDtCollect(dtCollect);
	}

	@Override
	public Product createOrUpdate(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product findById(String id) {
		return productRepository.findOneById(id);
	}

	@Override
	public void delete(String id) {
		productRepository.deleteById(id);
	}

	@Override
	public Page<Product> findAll(int page, int count) {
		return this.productRepository.findAll(PageRequest.of(page, count));
	}
}
