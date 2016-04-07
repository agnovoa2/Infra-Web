package es.uvigo.esei.infraestructura.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "MaterialPetitionRow")
@NamedQuery(name = "findAllMaterialPetitionRows", query="select m from MaterialPetitionRow m")
public class MaterialPetitionRow {
	@Id
	@Column(name = "materialRowPetitionNumber")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int materialRowPetitionNumber;
}
