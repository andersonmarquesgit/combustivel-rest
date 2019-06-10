package com.combustivel.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_state")
@Getter
@Setter
@NoArgsConstructor
public class State {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private String id;
	
	@Column(name = "region")
	private String region;
	
	@Column(name = "state")
	private String state;

	public State(String region, String state) {
		this.region = region;
		this.state = state;
	}
}
