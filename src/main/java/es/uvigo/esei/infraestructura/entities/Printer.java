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
@NamedQuery(name = "findAllPrinters", query = "Select p From Printer p")
public class Printer {
	@Id
	@JoinColumn
	@Column(name = "inventoryNumber", length = 100)
	private int inventoryNumber;

	@Column(length = 100, nullable = false)
	private String ubication;

	@ManyToMany(mappedBy = "printers")
	private List<User> users;

	@OneToMany(mappedBy = "printer")
	private List<Petition> consumables;

	@ManyToOne
	@JoinColumn(name = "modelName")
	private Model model;

	// Constructor required for JPA framework
	Printer() {
	}

	public Printer(int inventoryNumber, String ubication) {
		this.inventoryNumber = inventoryNumber;
		this.ubication = ubication;
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

	public void setModel(Model model) {
		this.model = model;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((consumables == null) ? 0 : consumables.hashCode());
		result = prime * result + inventoryNumber;
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((ubication == null) ? 0 : ubication.hashCode());
		result = prime * result + ((users == null) ? 0 : users.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			if (((Printer) obj).getInventoryNumber() == this.getInventoryNumber())
				return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	
	
}
