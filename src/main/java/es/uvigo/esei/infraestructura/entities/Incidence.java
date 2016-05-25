package es.uvigo.esei.infraestructura.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Incidence")
@NamedQuery(name = "findAllIncidences", query = "select i from Incidence i")
public class Incidence {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "description")
	private String description;

	// 0 incidence done, 1 incidence in process, 2 incidence resolved
	@Column(name = "state")
	private int state;

	@Column(name = "date")
	private Date date;

	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "computer")
	private Computer computer;
	
	@ManyToMany
	@JoinTable(name = "INC_TYPE", joinColumns = @JoinColumn(name = "id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "type", referencedColumnName = "type"))
	private List<IncidenceType> types;
	
	Incidence() {
	}

	public Incidence(String description, Date date) {
		this.description = description;
		this.state = 0;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Computer getComputer() {
		return computer;
	}

	public void setComputer(Computer computer) {
		this.computer = computer;
	}

	public List<IncidenceType> getTypes() {
		return types;
	}

	public void setTypes(List<IncidenceType> types) {
		this.types = types;
	}

	@Override
	public String toString() {
		String toRet = "Categorías: ";
		for (IncidenceType incidenceType : this.getTypes()) {
			toRet += incidenceType.getType() + " ";
		}
		return toRet + "\nDescripción: " + this.getDescription();
	}
	
	
}
