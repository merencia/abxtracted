package com.abxtract.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ExperimentCreationDto {

	private String projectId;
	private String name;
	private String key;
	private List<String> checkpoints;
	private List<ScenarioCreationDto> scenarios;

	@Getter
	@AllArgsConstructor
	@Builder
	public static class ScenarioCreationDto {
		private String name;
		private String key;
		private Integer rate;
	}
}
