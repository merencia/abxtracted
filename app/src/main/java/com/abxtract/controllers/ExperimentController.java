package com.abxtract.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abxtract.dtos.ExperimentCreationDto;
import com.abxtract.dtos.ExperimentDto;
import com.abxtract.interactions.experiment.ExperimentCreation;
import com.abxtract.models.Project;
import com.abxtract.models.Tenant;
import com.abxtract.repositories.ProjectRepository;
import com.abxtract.repositories.TenantRepository;

@RestController
@RequestMapping(value = "projects/{projectId}/experiments", consumes = MediaType.APPLICATION_JSON_VALUE)
public class ExperimentController {

	@Autowired
	private ExperimentCreation experimentCreation;

	@Autowired
	private TenantRepository tenantRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@RequestMapping(method = RequestMethod.POST)
	public ExperimentDto create(@PathVariable("projectId") String projectId, @RequestBody ExperimentCreationDto dto) {
		Tenant tenant = new Tenant();
		tenantRepository.save( tenant );
		Project project = projectRepository.findOne( projectId );
		if (project == null) {
			project = Project.builder().tenant( tenant ).id( projectId ).build();
			projectRepository.save( project );
		}
		dto.setProjectId( projectId );
		return new ExperimentDto( experimentCreation.create( project, dto ) );
	}
}
