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
@Table(name = "ConsumablePetitionRow")
@NamedQuery(name="findAllConsumablePetitionRows",query="Select p From ConsumablePetitionRow p")
public class ConsumablePetitionRow {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "petitionNumber")
	private ConsumablePetition petition;
	
	@ManyToOne
	@JoinColumn(name = "consumable")
	private Consumable consumable;
	
	@Column(name="quantity")
	private int quantity;
	
	ConsumablePetitionRow(){}

	public ConsumablePetitionRow(ConsumablePetition petition, Consumable consumable, int quantity){
		this.petition = petition;
		this.consumable = consumable;
		this.quantity = quantity;
	}
	
	public ConsumablePetition getPetition() {
		return petition;
	}

	public void setPetition(ConsumablePetition petition) {
		this.petition = petition;
	}

	public Consumable getConsumable() {
		return consumable;
	}

	public void setConsumable(Consumable consumable) {
		this.consumable = consumable;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}
}
