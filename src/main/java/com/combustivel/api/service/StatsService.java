package com.combustivel.api.service;

import java.math.BigDecimal;
import java.util.List;

import com.combustivel.api.response.AverageValues;

public interface StatsService {

	BigDecimal averageFuelPriceCity(String city);

	List<AverageValues> averageByCity();
	
	List<AverageValues> averageByFlag();

}
