package com.abxtract.interactions.experiment;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.abxtract.dtos.ExperimentCreationDto;
import com.abxtract.models.Experiment;
import com.abxtract.models.Scenario;
import com.abxtract.models.Tenant;
import com.abxtract.repositories.ExperimentRepository;

public class ExperimentCreationTest {

	private final Tenant tenant = Tenant.builder().id( "some-tenant-id" ).build();

	@InjectMocks
	public ExperimentCreation experimentCreation;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Mock
	public ExperimentRepository experimentRepository;

	@Before
	public void setup() {
		initMocks( this );
	}

	@Test
	public void testCreatePassingNull() {
		exception.expect( IllegalArgumentException.class );
		exception.expectMessage( "Experiment data is required!" );
		experimentCreation.create( tenant, null );
	}

	@Test
	public void testCreateWithoutProjectId() {
		exception.expect( IllegalArgumentException.class );
		exception.expectMessage( "Project id is required!" );
		ExperimentCreationDto experiment = buildExperimentData();
		experiment.setProjectId( "" );
		experimentCreation.create( tenant, experiment );
	}

	@Test
	public void testCreateWithoutExperimentName() {
		exception.expect( IllegalArgumentException.class );
		exception.expectMessage( "Experiment name cannot be blank!" );
		ExperimentCreationDto experiment = buildExperimentData();
		experiment.setName( "" );
		experimentCreation.create( tenant, experiment );
	}

	@Test
	public void testCreateWithoutExperimentKey() {
		exception.expect( IllegalArgumentException.class );
		exception.expectMessage( "Experiment key cannot be blank!" );
		ExperimentCreationDto experiment = buildExperimentData();
		experiment.setKey( "" );
		experimentCreation.create( tenant, experiment );
	}

	@Test
	public void testCreateWithoutChekpoints() {
		exception.expect( IllegalArgumentException.class );
		exception.expectMessage( "An experiment must have at least one checkpoint!" );
		ExperimentCreationDto experiment = buildExperimentData();
		experiment.setCheckpoints( Collections.EMPTY_LIST );
		experimentCreation.create( tenant, experiment );
	}

	@Test
	public void testCreateWithoutScenarios() {
		exception.expect( IllegalArgumentException.class );
		exception.expectMessage( "An experiment must have at least two scenarios!" );
		ExperimentCreationDto experiment = buildExperimentData();
		experiment.setScenarios( Collections.EMPTY_LIST );
		experimentCreation.create( tenant, experiment );
	}

	@Test
	public void testCreateWithoutScenarioName() {
		exception.expect( IllegalArgumentException.class );
		exception.expectMessage( "A scenario name cannot be blank!" );
		ExperimentCreationDto experiment = buildExperimentData();
		experiment.getScenarios().get( 0 ).setName( "" );
		experimentCreation.create( tenant, experiment );
	}

	@Test
	public void testCreateWithoutScenarioKey() {
		exception.expect( IllegalArgumentException.class );
		exception.expectMessage( "A scenario key cannot be blank!" );
		ExperimentCreationDto experiment = buildExperimentData();
		experiment.getScenarios().get( 0 ).setKey( "" );
		experimentCreation.create( tenant, experiment );
	}

	@Test
	public void testCreateWithoutScenarioRate() {
		exception.expect( IllegalArgumentException.class );
		exception.expectMessage( "A scenario rate cannot be null!" );
		ExperimentCreationDto experiment = buildExperimentData();
		experiment.getScenarios().get( 0 ).setRate( null );
		experimentCreation.create( tenant, experiment );
	}

	@Test
	public void testCreateWithout100PercentRate() {
		exception.expect( IllegalArgumentException.class );
		exception.expectMessage( "The sum of the scenario rates must be 100!" );
		ExperimentCreationDto experiment = buildExperimentData();
		experiment.getScenarios().get( 0 ).setRate( 20 );
		experimentCreation.create( tenant, experiment );
	}

	@Test
	public void testCrateExperiment() {
		ExperimentCreationDto experimentData = buildExperimentData();
		Experiment experiment = experimentCreation.create( tenant, experimentData );

		assertThat( experiment.getName(), equalTo( experimentData.getName() ) );
		assertThat( experiment.getKey(), equalTo( experimentData.getKey() ) );
		assertThat( experiment.getTenant(), equalTo( tenant ) );
		assertThat( experiment.getScenarios(), hasSize( 2 ) );
		Scenario firstScenario = experiment.getScenarios().get( 0 );
		assertThat( firstScenario.getName(), equalTo( experimentData.getScenarios().get( 0 ).getName() ) );
		assertThat( firstScenario.getKey(), equalTo( experimentData.getScenarios().get( 0 ).getKey() ) );
		assertThat( firstScenario.getRate(), equalTo( experimentData.getScenarios().get( 0 ).getRate() ) );
		assertThat( firstScenario.getSplittedScenarios(), hasSize( 2 ) );
		assertThat( firstScenario.getSplittedScenarios().get( 0 ).getRate() + firstScenario.getSplittedScenarios()
				.get( 1 ).getRate(), equalTo( experimentData.getScenarios().get( 0 ).getRate().doubleValue() ) );
		verify( experimentRepository ).save( experiment );
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
