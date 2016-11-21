package com.abxtract.repositories;

import org.springframework.data.repository.CrudRepository;

import com.abxtract.models.Experiment;

public interface ExperimentRepository extends CrudRepository<Experiment, String> {
}
