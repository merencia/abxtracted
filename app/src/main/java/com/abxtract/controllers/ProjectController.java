package com.abxtract.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abxtract.models.Project;
import com.abxtract.models.Tenant;
import com.abxtract.repositories.ProjectRepository;
import com.abxtract.repositories.TenantRepository;

@RestController
@RequestMapping(value = "/projects", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private TenantRepository tenantRepository;

	@RequestMapping(method = RequestMethod.GET)
	public List<Project> list() {
		Tenant tenant = getAuthenticatedTenant();
		return projectRepository.findByTenantId( tenant.getId() );
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Project create(@RequestBody Project project) {
		Tenant tenant = getAuthenticatedTenant();
		project.setTenant( tenant );
		return projectRepository.save( project );
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public Project findById(@PathVariable String id) {
		return projectRepository.findOne( id );
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Project update(@PathVariable String id, @RequestBody Project project) {
		project.setId( id );
		return projectRepository.save( project );
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String id) {
		projectRepository.delete( id );
	}

	private Tenant getAuthenticatedTenant() {
		// TODO: obter tenant corrente
		Tenant tenant = new Tenant();
		return tenantRepository.save( tenant );
	}
}
