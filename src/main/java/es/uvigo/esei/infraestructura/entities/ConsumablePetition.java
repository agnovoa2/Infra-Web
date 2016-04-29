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
@Table(name = "ConsumablePetition")
@NamedQueries({
		@NamedQuery(name = "findAllPetitions", query = "Select p From ConsumablePetition p Where p.petitionState = 0"),
		@NamedQuery(name = "findAllDonePetitions", query = "Select p From ConsumablePetition p Where p.petitionState = 1") })
public class ConsumablePetition {	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int petitionNumber;

	@Column(name = "petitionDate")
	private Date petitionDate;

	@Column(name = "petitionState")
	private int petitionState;

	@ManyToOne
	@JoinColumn(name = "petitioner")
	private User user;

	@ManyToOne
	@JoinColumn(name = "printer")
	private Printer printer;

	@OneToMany(mappedBy = "consumable")
	private List<ConsumablePetitionRow> petitionRows;

	ConsumablePetition() {
	}

	public ConsumablePetition(Printer printer, Date petitionDate, User user) {
		this.petitionDate = petitionDate;
		this.user = user;
		this.printer = printer;
		this.petitionState = 0;
	}

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

	public List<ConsumablePetitionRow> getPetitionRows() {
		return petitionRows;
	}

	public void setPetitionRows(List<ConsumablePetitionRow> petitionRows) {
		this.petitionRows = petitionRows;
	}

	public int getPetitionState() {
		return petitionState;
	}

	public void setPetitionState(int petitionState) {
		this.petitionState = petitionState;
	}

	public Printer getPrinter() {
		return printer;
	}

	public void setPrinter(Printer printer) {
		this.printer = printer;
	}
}
