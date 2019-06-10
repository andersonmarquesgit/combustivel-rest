package com.combustivel.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceRequest {

	private String productId;
	private Double purchaseValue;
	private Double salesValue;
}
