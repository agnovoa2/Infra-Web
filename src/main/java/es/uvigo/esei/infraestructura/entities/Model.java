package es.uvigo.esei.infraestructura.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Model")
@NamedQuery(name = "findAllModels", query="select m from Model m where m.unused = false")
public class Model {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="modelName", length = 100, unique=true, nullable=false)
	private String modelName;

	@Column(length = 45, nullable = false)
	private String tradeMark;

	@Column(nullable = false)
	private boolean unused;
	
	@ManyToMany
	@JoinTable(name = "CONS_MODEL", joinColumns = @JoinColumn(name = "modelId", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "consumableId", referencedColumnName = "id"))
	private List<Consumable> consumables;
	
	@OneToMany(mappedBy="model")
	private List<Printer> printers;
	
	// Constructor required for JPA framework
	Model(){}
	
	public Model(String modelName, String tradeMark) {
		this.modelName = modelName;
		this.tradeMark = tradeMark;
		this.unused = false;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isUnused() {
		return unused;
	}

	public void setUnused(boolean unused) {
		this.unused = unused;
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
		try {
			if (((Model) obj).getId() == this.getId())
				return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	
}
