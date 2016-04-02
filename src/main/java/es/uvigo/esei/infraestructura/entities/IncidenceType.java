package es.uvigo.esei.infraestructura.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "IncidenceType")
@NamedQuery(name = "findAllIncidenceTypes", query = "select i from IncidenceType i")
public class IncidenceType {
	@Id
	@Column(name = "type")
	private String type;
	
	@ManyToMany(mappedBy="types")
	private List<Incidence> incidences;
	
	IncidenceType(){}
	
	IncidenceType(String type){
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Incidence> getIncidences() {
		return incidences;
	}

	public void setIncidences(List<Incidence> incidences) {
		this.incidences = incidences;
	}
}
