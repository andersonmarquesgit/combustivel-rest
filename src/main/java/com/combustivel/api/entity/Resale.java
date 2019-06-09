package com.combustivel.api.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_resale")
@Getter
@Setter
public class Resale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	
	@Column(name = "cod")
	private String cod;
	
	@Column(name = "desc")
	private String desc;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="fk_id_city")
	private City city;
	
	@OneToMany(mappedBy = "resale", cascade = CascadeType.PERSIST)
	private List<Product> products;

	public Resale(String region, String state, String city, 
			String desc, String cod) {
		this.cod = cod;
		this.desc = desc;
		this.city = new City(region, state, city);
	}
}
