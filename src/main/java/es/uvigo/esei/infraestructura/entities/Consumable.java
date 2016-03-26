package es.uvigo.esei.infraestructura.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Consumable")
@NamedQuery(name = "findAllConsumables", query="select c from Consumable c")
public class Consumable {

	@Id
	@JoinColumn
	@Column(name="consumableName", length = 100)
	private String login;

	@Column(length = 12, nullable = false)
	@Enumerated(EnumType.STRING)
	private ConsumableType consumableType;

	@Column(length = 45, nullable = false)
	private String colour;

	@Column(length = 45, nullable = false)
	private String description;

	@ManyToMany(mappedBy="consumables")
	private List<Model> models;

	// Constructor required for JPA framework
	Consumable() {}

	
}
