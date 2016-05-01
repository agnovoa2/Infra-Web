package es.uvigo.esei.infraestructura.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "MaterialPetition")
@NamedQueries({
		@NamedQuery(name = "findAllMaterialPetitions", query = "select m from MaterialPetition m where m.petitionState = 0"),
		@NamedQuery(name = "findAllDoneMaterialPetitions", query = "select m from MaterialPetition m where m.petitionState = 1"),
		@NamedQuery(name = "findAllRetrievedMaterialPetitions", query = "select m from MaterialPetition m where m.petitionState = 2") })
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

	@OneToMany(mappedBy = "material")
	private List<MaterialPetitionRow> petitionRows;

	MaterialPetition() {
	}

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

	public List<MaterialPetitionRow> getPetitionRows() {
		return petitionRows;
	}

	public void setPetitionRows(List<MaterialPetitionRow> petitionRows) {
		this.petitionRows = petitionRows;
	}
}
