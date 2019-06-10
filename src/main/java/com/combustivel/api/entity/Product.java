package com.combustivel.api.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_product")
@Getter
@Setter
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	
	@Column(name = "desc")
	private String desc;
	
	@Column(name = "purchase_value")
	private Double purchaseValue;
	
	@Column(name = "sales_value")
	private Double salesValue;
	
	@Column(name = "dt_collect")
    private LocalDate dtCollect;
	
	@Column(name = "flag")
	private String flag;
	
	@Column(name = "unity")
	private String unity;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Resale resale;

	public Product(String region, String state, String city, 
			String cod, String descRevenda, String descProduct, 
			LocalDate dtCollect, Double purchaseValue, Double salesValue, 
			String unity, String flag) {
		this.desc = descProduct;
		this.purchaseValue = purchaseValue;
		this.salesValue = salesValue;
		this.dtCollect = dtCollect;
		this.flag = flag;
		this.unity = unity;
		this.resale = new Resale(region, state, city, cod, descRevenda);
	}

}
