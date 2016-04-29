package es.uvigo.esei.infraestructura.entities;

import java.sql.Date;

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
@Table(name = "MaterialPetition")
@NamedQuery(name = "findAllMaterialPetitions", query="select m from MaterialPetition m")
public class MaterialPetition {
	@Id
	@Column(name = "materialPetitionNumber")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int materialPetitionNumber;
	
	@Column(name = "petitionDate")
	private Date petitionDate;

	@Column(name = "petitionState")
	private int petitionState;
	
	@ManyToOne
	@JoinColumn(name = "petitioner")
	private User user;
	
	MaterialPetition(){}

	public MaterialPetition(Date petitionDate, User user) {
		this.petitionState = 0;
		this.petitionDate = petitionDate;
		this.user = user;
	}

	public int getMaterialPetitionNumber() {
		return materialPetitionNumber;
	}

	public void setMaterialPetitionNumber(int materialPetitionNumber) {
		this.materialPetitionNumber = materialPetitionNumber;
	}

	public Date getPetitionDate() {
		return petitionDate;
	}

	public void setPetitionDate(Date petitionDate) {
		this.petitionDate = petitionDate;
	}

	public int getPetitionState() {
		return petitionState;
	}

	public void setPetitionState(int petitionState) {
		this.petitionState = petitionState;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
