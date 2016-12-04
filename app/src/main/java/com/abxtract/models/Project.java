package com.abxtract.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "projects")
@Getter
@Setter
public class Project extends Model {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;

	private String name;

	@ManyToOne
	private Tenant tenant;
}
