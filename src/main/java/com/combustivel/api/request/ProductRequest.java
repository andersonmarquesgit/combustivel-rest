package com.combustivel.api.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

	private String descProduct;
	private Double purchaseValue;
	private Double salesValue;
    private LocalDate dtCollect;
	private String flag;
	private String unity;
	private String codResale;
	private String descResale;
	private String descCity;
	private String siglaRegion;
	private String descState;
}
