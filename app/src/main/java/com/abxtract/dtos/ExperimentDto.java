package com.abxtract.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import com.abxtract.models.Experiment;

@Getter
@AllArgsConstructor
@Builder
public class ExperimentDto {
	private final String id;
	private final String name;
	private final String key;

	public ExperimentDto(Experiment experiment) {
		this.id = experiment.getId();
		this.name = experiment.getName();
		this.key = experiment.getKey();
	}
}
