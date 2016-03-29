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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Petition")
@NamedQuery(name="findAllPetitions",query="Select p From Petition p")
public class Petition{
	
	@Id
	@Column(name="petitionNumber")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int petitionNumber;
	
	@Column(name="petitionDate")
	private Date petitionDate;
	
	@ManyToOne
	@JoinColumn(name = "petitioner")
	private User user;
	
	@OneToMany(mappedBy="petition")
	private List<PetitionRow> petitionRows;
	
	Petition(){}

	public int getPetitionNumber() {
		return petitionNumber;
	}

	public void setPetitionNumber(int petitionNumber) {
		this.petitionNumber = petitionNumber;
	}

	public Date getPetitionDate() {
		return petitionDate;
	}

	public void setPetitionDate(Date petitionDate) {
		this.petitionDate = petitionDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<PetitionRow> getPetitionRows() {
		return petitionRows;
	}

	public void setPetitionRows(List<PetitionRow> petitionRows) {
		this.petitionRows = petitionRows;
	}
}
