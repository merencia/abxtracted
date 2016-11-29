package com.abxtract.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "scenarios")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Scenario extends Model {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;

	@ManyToOne
	private Experiment experiment;

	private String name;

	private String key;

	private Integer rate;

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
	private List<SplittedScenario> splittedScenarios;
}
