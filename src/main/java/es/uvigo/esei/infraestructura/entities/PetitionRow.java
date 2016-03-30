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
@Table(name = "PetitionRow")
@NamedQuery(name="findAllPetitionRows",query="Select p From PetitionRow p")
public class PetitionRow {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int ID;
	
	@ManyToOne
	@JoinColumn(name = "petition")
	private Petition petition;
	
	@ManyToOne
	@JoinColumn(name = "consumable")
	private Consumable consumable;
	
	@ManyToOne
	@JoinColumn(name = "printer")
	private Printer printer;
	
	@Column(name="quantity")
	private int quantity;
	
	PetitionRow(){}

	public PetitionRow(Petition petition, Consumable consumable, Printer printer, int quantity){
		this.petition = petition;
		this.consumable = consumable;
		this.printer = printer;
		this.quantity = quantity;
	}
	
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

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
}
