package com.combustivel.api.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.combustivel.api.repository.ProductRepository;
import com.combustivel.api.response.AverageValues;
import com.combustivel.api.service.StatsService;

@Service
public class StasServiceImpl implements StatsService {

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public BigDecimal averageFuelPriceCity(String city) {
		return productRepository.averageFuelPriceCity(city);
	}

	@Override
	public AverageValues averageByCity() {
		return productRepository.averageByCity();
	}

}
