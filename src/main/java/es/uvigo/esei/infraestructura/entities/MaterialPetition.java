package es.uvigo.esei.infraestructura.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "MaterialPetition")
@NamedQuery(name = "findAllMaterialPetitions", query="select m from MaterialPetition m")
public class MaterialPetition {
	@Id
	@Column(name = "materialPetitionNumber")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int materialPetitionNumber;
}
