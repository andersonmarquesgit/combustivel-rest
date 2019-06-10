package com.combustivel.api.service;

import java.math.BigDecimal;

import com.combustivel.api.response.AverageValues;

public interface StatsService {

	BigDecimal averageFuelPriceCity(String city);

	AverageValues averageByCity();

}
