package com.abxtract.interactions.experiment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abxtract.dtos.ExperimentCreationDto;
import com.abxtract.models.Experiment;
import com.abxtract.models.Project;
import com.abxtract.models.Scenario;
import com.abxtract.models.SplittedScenario;
import com.abxtract.repositories.ExperimentRepository;
import com.abxtract.repositories.ProjectRepository;
import com.google.common.base.Strings;

@Service
public class ExperimentCreation {

	@Autowired
	private ExperimentRepository experimentRepository;

	@Autowired
	private ProjectRepository projectRepository;

	public Experiment create(Project project, ExperimentCreationDto experimentData) {
		validateExperimentData( experimentData );
		Experiment experiment = Experiment.builder()
				.key( experimentData.getKey() )
				.name( experimentData.getName() )
				.project( project ).build();

		experiment.setScenarios( buildScenarios( experimentData, experiment ) );
		experimentRepository.save( experiment );

		return experiment;
	}

	private List<Scenario> buildScenarios(ExperimentCreationDto experimentData, Experiment experiment) {
		return experimentData.getScenarios().stream().map( scenarioDto -> {
			Scenario scenario = Scenario.builder()
					.name( scenarioDto.getName() )
					.key( scenarioDto.getKey() )
					.rate( scenarioDto.getRate() )
					.experiment( experiment )
					.build();

			scenario.setSplittedScenarios( buildSplittedScenarios( scenario ) );
			return scenario;
		} ).collect( Collectors.toList() );
	}

	private List<SplittedScenario> buildSplittedScenarios(Scenario scenario) {
		return Arrays.asList( SplittedScenario.builder()
						.scenario( scenario )
						.rate( scenario.getRate() / 2D )
						.build(),
				SplittedScenario.builder()
						.scenario( scenario )
						.rate( scenario.getRate() / 2D )
						.build()
		);
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
			throw new IllegalArgumentException( "An experiment must have at least one checkpoint!" );

		validateScenarios( experimentData );
	}

	private void validateScenarios(ExperimentCreationDto experimentData) {
		if (experimentData.getScenarios() == null || experimentData.getScenarios().size() < 2)
			throw new IllegalArgumentException( "An experiment must have at least two scenarios!" );

		experimentData.getScenarios().forEach( scenario -> {
			validateScenario( scenario );
		} );

		Integer toatalRate = experimentData.getScenarios().stream()
				.mapToInt( ExperimentCreationDto.ScenarioCreationDto::getRate ).sum();
		if (toatalRate != 100)
			throw new IllegalArgumentException( "The sum of the scenario rates must be 100!" );
	}

	private void validateScenario(ExperimentCreationDto.ScenarioCreationDto scenario) {
		if (Strings.isNullOrEmpty( scenario.getName() ))
			throw new IllegalArgumentException( "A scenario name cannot be blank!" );

		if (Strings.isNullOrEmpty( scenario.getKey() ))
			throw new IllegalArgumentException( "A scenario key cannot be blank!" );

		if (scenario.getRate() == null)
			throw new IllegalArgumentException( "A scenario rate cannot be null!" );
	}
}
