package com.abxtract.interactions.experiment;

import org.springframework.stereotype.Service;

import com.abxtract.dtos.ExperimentCreationDto;
import com.abxtract.models.Experiment;
import com.google.common.base.Strings;

@Service
public class ExperimentCreation {

	public Experiment create(ExperimentCreationDto experimentData) {
		validateExperimentData( experimentData );
		return null;
	}

	private void validateExperimentData(ExperimentCreationDto experimentData) {
		if (experimentData == null)
			throw new IllegalArgumentException( "Experiment data is required!" );

		if (Strings.isNullOrEmpty( experimentData.getProjectId() ))
			throw new IllegalArgumentException( "Project id is required!" );

		if (Strings.isNullOrEmpty( experimentData.getName() ))
			throw new IllegalArgumentException( "Experiment name cannot be blank!" );

		if (Strings.isNullOrEmpty( experimentData.getKey() ))
			throw new IllegalArgumentException( "Experiment key cannot be blank!" );

		if (experimentData.getCheckpoints() == null || experimentData.getCheckpoints().isEmpty())
			throw new IllegalArgumentException( "EAn experiment must have at least one checkpoint!" );
	}
}
