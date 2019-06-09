package com.combustivel.api.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_city")
@Getter
@Setter
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	
	@Column(name = "city")
	private String city;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="fk_id_state")
	private State state;

	public City(String region, String state, String city) {
		this.city = city;
		this.state = new State(region, state);
	}
}
