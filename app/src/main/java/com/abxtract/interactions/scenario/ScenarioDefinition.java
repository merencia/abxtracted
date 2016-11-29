package com.abxtract.interactions.scenario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abxtract.models.Customer;
import com.abxtract.repositories.ExperimentRepository;

@Service
public class ScenarioDefinition {

	@Autowired
	private ExperimentRepository experimentRepository;

	private void raffle(String experimentId, Customer customer) {
		
	}
}
