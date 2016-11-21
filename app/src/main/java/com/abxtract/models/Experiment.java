package com.abxtract.models;

import java.io.Serializable;
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
@Table(name = "experiments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Experiment extends Model implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private Long id;

	@ManyToOne
	private Tenant tenant;

	private String name;

	private String key;

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
	private List<Scenario> scenarios;
}
