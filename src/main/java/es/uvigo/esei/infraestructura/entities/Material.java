package es.uvigo.esei.infraestructura.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Material")
@NamedQuery(name = "findAllMaterials", query="select m from Material m")
public class Material {
	
	@Id
	@Column(name = "materialPetitionNumber")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int materialPetitionNumber;
	
	@Column(name="materialName", length = 45)
	private String materialName;
	
	Material(){}
}
