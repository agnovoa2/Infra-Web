package es.uvigo.esei.infraestructura.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

	@Column(length = 45, nullable = false)
	private String tradeMark;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "CONS_MODEL", joinColumns = @JoinColumn(name = "modelName", referencedColumnName = "modelName"), 
		inverseJoinColumns = @JoinColumn(name = "consumableName", referencedColumnName = "consumableName"))
	private List<Consumable> consumables;
	
	@OneToMany(mappedBy="model",fetch = FetchType.EAGER)
	private List<Printer> printers;
	
	// Constructor required for JPA framework
	Model(){}
	
	public Model(String modelName, String tradeMark) {
		super();
		this.modelName = modelName;
		this.tradeMark = tradeMark;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((consumables == null) ? 0 : consumables.hashCode());
		result = prime * result + ((modelName == null) ? 0 : modelName.hashCode());
		result = prime * result + ((printers == null) ? 0 : printers.hashCode());
		result = prime * result + ((tradeMark == null) ? 0 : tradeMark.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Model other = (Model) obj;
		if (consumables == null) {
			if (other.consumables != null)
				return false;
		} else if (!consumables.equals(other.consumables))
			return false;
		if (modelName == null) {
			if (other.modelName != null)
				return false;
		} else if (!modelName.equals(other.modelName))
			return false;
		if (printers == null) {
			if (other.printers != null)
				return false;
		} else if (!printers.equals(other.printers))
			return false;
		if (tradeMark == null) {
			if (other.tradeMark != null)
				return false;
		} else if (!tradeMark.equals(other.tradeMark))
			return false;
		return true;
	}
	
	
}
