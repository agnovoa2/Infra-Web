package es.uvigo.esei.infraestructura.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "MaterialPetitionRow")
@NamedQuery(name = "findAllMaterialPetitionRows", query = "select m from MaterialPetitionRow m order by m.material.materialName")
public class MaterialPetitionRow {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "quantity")
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "materialPetitionNumber")
	private MaterialPetition materialPetition;

	@ManyToOne
	@JoinColumn(name = "material")
	private Material material;
	
	MaterialPetitionRow(){}

	public MaterialPetitionRow(int quantity, MaterialPetition materialPetition, Material material) {
		this.quantity = quantity;
		this.materialPetition = materialPetition;
		this.material = material;
	}

	public int getId() {
		return id;
	}

	public void setId(int materialRowPetitionNumber) {
		this.id = materialRowPetitionNumber;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public MaterialPetition getMaterialPetition() {
		return materialPetition;
	}

	public void setMaterialPetition(MaterialPetition materialPetition) {
		this.materialPetition = materialPetition;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + quantity;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			if (((MaterialPetitionRow) obj).getMaterial().getId() == this.getMaterial().getId() && 
					((MaterialPetitionRow) obj).getMaterialPetition().getMaterialPetitionNumber() == this.getMaterialPetition().getMaterialPetitionNumber())
				return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	
	
}
