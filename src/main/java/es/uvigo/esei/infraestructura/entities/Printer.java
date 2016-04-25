package es.uvigo.esei.infraestructura.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Printer")
@NamedQuery(name = "findAllPrinters", query = "Select p From Printer p Where p.unused = false")
public class Printer {
	
	@Id
	@Column(name = "inventoryNumber", length = 100)
	private int inventoryNumber;

	@Column(length = 100, nullable = false)
	private String ubication;

	@Column(nullable = false)
	private boolean unused;
	
	@ManyToMany(mappedBy = "printers")
	private List<User> users;

	@ManyToOne
	@JoinColumn(name = "modelName")
	private Model model;

	@OneToMany(mappedBy = "printer")
	private List<ConsumablePetition> petitions;

	// Constructor required for JPA framework
	Printer() {
	}

	public Printer(int inventoryNumber, String ubication) {
		this.inventoryNumber = inventoryNumber;
		this.ubication = ubication;
		this.unused = false;
	}

	public int getInventoryNumber() {
		return inventoryNumber;
	}

	public void setInventoryNumber(int inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}

	public String getUbication() {
		return ubication;
	}

	public void setUbication(String ubication) {
		this.ubication = ubication;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Model getModel() {
		return model;
	}

	public boolean isUnused() {
		return unused;
	}

	public void setUnused(boolean unused) {
		this.unused = unused;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public List<ConsumablePetition> getPetitions() {
		return petitions;
	}

	public void setPetitions(List<ConsumablePetition> petitions) {
		this.petitions = petitions;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Printer other = (Printer) obj;
		if (inventoryNumber != other.inventoryNumber)
			return false;
		return true;
	}

}
