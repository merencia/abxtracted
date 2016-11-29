package com.abxtract.repositories;

import org.springframework.data.repository.CrudRepository;

import com.abxtract.models.Project;

public interface ProjectRepository extends CrudRepository<Project, String> {
}
