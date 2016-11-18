package com.abxtract.interactions.experiment;

import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;

import com.abxtract.dtos.ExperimentCreationDto;

public class ExperimentCreationTest {

	@InjectMocks
	public ExperimentCreation experimentCreation;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {
		initMocks( this );
	}

	@Test
	public void testCreatePassingNull() {
		exception.expect( IllegalArgumentException.class );
		exception.expectMessage( "Experiment data is required!" );
		experimentCreation.create( null );
	}

	@Test
	public void testCreateWithoutProjectId() {
		exception.expect( IllegalArgumentException.class );
		exception.expectMessage( "Project id is required!" );
		ExperimentCreationDto experiment = buildExperimentData();
		experiment.setProjectId( "" );
		experimentCreation.create( experiment );
	}

	@Test
	public void testCreateWithoutExperimentName() {
		exception.expect( IllegalArgumentException.class );
		exception.expectMessage( "Experiment name cannot be blank!" );
		ExperimentCreationDto experiment = buildExperimentData();
		experiment.setName( "" );
		experimentCreation.create( experiment );
	}

	@Test
	public void testCreateWithoutExperimentKey() {
		exception.expect( IllegalArgumentException.class );
		exception.expectMessage( "Experiment key cannot be blank!" );
		ExperimentCreationDto experiment = buildExperimentData();
		experiment.setKey( "" );
		experimentCreation.create( experiment );
	}

	@Test
	public void testCreateWithoutChekpoints() {
		exception.expect( IllegalArgumentException.class );
		exception.expectMessage( "An experiment must have at least one checkpoint!" );
		ExperimentCreationDto experiment = buildExperimentData();
		experiment.setCheckpoints( Collections.EMPTY_LIST );
		experimentCreation.create( experiment );
	}

	private ExperimentCreationDto buildExperimentData() {
		ExperimentCreationDto.ScenarioCreationDto control = ExperimentCreationDto.ScenarioCreationDto.builder()
				.name( "control" )
				.key( "control" )
				.rate( 50 )
				.build();

		ExperimentCreationDto.ScenarioCreationDto test = ExperimentCreationDto.ScenarioCreationDto.builder()
				.name( "test" )
				.key( "test" )
				.rate( 50 )
				.build();

		return ExperimentCreationDto.builder()
				.projectId( "some-project-uuid" )
				.name( "Name" )
				.key( "key" )
				.checkpoints( Arrays.asList( "checkpint" ) )
				.scenarios( Arrays.asList( control, test ) )
				.build();
	}
}
