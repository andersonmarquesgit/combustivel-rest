package com.combustivel.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AverageValues {

	private Double avgPurchaseValue;
	
	private Double avgSalesValue;
	
	private String givenGrouper;

	public AverageValues(Double avgPurchaseValue, Double avgSalesValue, String givenGrouper) {
		this.avgPurchaseValue = avgPurchaseValue;
		this.avgSalesValue = avgSalesValue;
		this.givenGrouper = givenGrouper;
	}

	
}
