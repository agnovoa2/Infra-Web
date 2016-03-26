package es.uvigo.esei.infraestructura.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Model")
@NamedQuery(name = "findAllModels", query="select m from Model m")
public class Model {
	
	@Id
	@JoinColumn
	@Column(name="modelName", length = 100)
	private String modelName;

	@Column(length = 12, nullable = false)
	private String tradeMark;

	@ManyToMany
	@JoinTable(name = "CONS_MODEL", joinColumns = @JoinColumn(name = "modelName", referencedColumnName = "modelName"), 
		inverseJoinColumns = @JoinColumn(name = "consumableName", referencedColumnName = "consumableName"))
	private List<Consumable> consumables;
	
	@OneToMany(mappedBy="model")
	private List<Printer> printers;
	
	// Constructor required for JPA framework
	Model(){}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getTradeMark() {
		return tradeMark;
	}

	public void setTradeMark(String tradeMark) {
		this.tradeMark = tradeMark;
	}

	public List<Consumable> getConsumables() {
		return consumables;
	}

	public void setConsumables(List<Consumable> consumables) {
		this.consumables = consumables;
	}

	public List<Printer> getPrinters() {
		return printers;
	}

	public void setPrinters(List<Printer> printers) {
		this.printers = printers;
	}
	
	
}
