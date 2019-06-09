package com.combustivel.api.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.combustivel.api.repository.ProductRepository;
import com.combustivel.api.service.CombustivelService;

@Service
public class CombustivelServiceImpl implements CombustivelService {

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public BigDecimal averageFuelPriceCity(String city) {
		return productRepository.averageFuelPriceCity(city);
	}

}
