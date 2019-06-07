package com.combustivel.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stats {

	@JsonProperty("count_mutant_dna")
	private Long countMutantDna;
	
	@JsonProperty("count_human_dna")
	private Long countHumanDna;
	
	@JsonProperty("ration")
	private Float ration;

	public Stats(Long countMutantDna, Long countHumanDna, Float ration) {
		this.countMutantDna = countMutantDna;
		this.countHumanDna = countHumanDna;
		this.ration = ration;
	}
}
