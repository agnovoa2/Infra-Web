package es.uvigo.esei.infraestructura.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@IdClass(PetitionRowId.class)
@Table(name = "PetitionRow")
@NamedQuery(name="findAllPetitionRows",query="Select p From PetitionRow p")
public class PetitionRow {
	@Id
	@ManyToOne
	@JoinColumn(name = "petition")
	private Petition petition;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "consumable")
	private Consumable consumable;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "printer")
	private Printer printer;
	
	@Column(name="quantity")
	private int quantity;
	
	PetitionRow(){}

	public Petition getPetition() {
		return petition;
	}

	public void setPetition(Petition petition) {
		this.petition = petition;
	}

	public Consumable getConsumable() {
		return consumable;
	}

	public void setConsumable(Consumable consumable) {
		this.consumable = consumable;
	}

	public Printer getPrinter() {
		return printer;
	}

	public void setPrinter(Printer printer) {
		this.printer = printer;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
