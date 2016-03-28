package es.uvigo.esei.infraestructura.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Petition")
@IdClass(PetitionId.class)
public class Petition{
	
	@Id
    @ManyToOne
    @JoinColumn(name = "inventoryNumber", referencedColumnName = "inventoryNumber")
    private Printer printer;
	
	@Id
    @ManyToOne
    @JoinColumn(name = "consumableName", referencedColumnName = "consumableName")
    private Consumable consumable;
	
	@Column(name = "petitionNumber")
	private int petitionNumber;
	
	@Column(name = "quantity")
	private int quantity;

	@Column(name = "petitionDate")
	private Date petitionDate;

	// 0 petition done 1 petition solved
	@Column(name = "state")
	private int state;

	Petition() {
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getPetitionDate() {
		return petitionDate;
	}

	public void setPetitionDate(Date petitionDate) {
		this.petitionDate = petitionDate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getPetitionNumber() {
		return petitionNumber;
	}

	public void setPetitionNumber(int petitionNumber) {
		this.petitionNumber = petitionNumber;
	}
}
