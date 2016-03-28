package es.uvigo.esei.infraestructura.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Consumable")
@NamedQueries({ @NamedQuery(name = "findAllConsumables", query = "select c from Consumable c"),
		@NamedQuery(name = "findAllBlackConsumables", query = "select c from Consumable c Where c.colour = 'Negro'"),
		@NamedQuery(name = "findAllPhotoBlackConsumables", query = "select c from Consumable c Where c.colour = 'Negro fotográfico'"),
		@NamedQuery(name = "findAllCyanConsumables", query = "select c from Consumable c Where c.colour = 'Cyan'"),
		@NamedQuery(name = "findAllLightCyanConsumables", query = "select c from Consumable c Where c.colour = 'Cyan claro'"),
		@NamedQuery(name = "findAllMagentaConsumables", query = "select c from Consumable c Where c.colour = 'Magenta'"),
		@NamedQuery(name = "findAllLightMagentaConsumables", query = "select c from Consumable c Where c.colour = 'Magenta claro'"),
		@NamedQuery(name = "findAllYellowConsumables", query = "select c from Consumable c Where c.colour = 'Amarillo'"),
		@NamedQuery(name = "findAllTricolorConsumables", query = "select c from Consumable c Where c.colour = 'Tricolor'"),
		@NamedQuery(name = "findAllDrumConsumables", query = "select c from Consumable c Where c.consumableType = es.uvigo.esei.infraestructura.entities.ConsumableType.DRUM"),
		@NamedQuery(name = "findAllFuserConsumables", query = "select c from Consumable c Where c.consumableType = es.uvigo.esei.infraestructura.entities.ConsumableType.FUSER"),
		@NamedQuery(name = "findAllBeltUnitConsumables", query = "select c from Consumable c Where c.consumableType = es.uvigo.esei.infraestructura.entities.ConsumableType.BELT_UNIT"),
		@NamedQuery(name = "findAllGarbageUnitConsumables", query = "select c from Consumable c Where c.consumableType = es.uvigo.esei.infraestructura.entities.ConsumableType.GARBAGE_UNIT"),
		@NamedQuery(name = "findAllTransferKitConsumables", query = "select c from Consumable c Where c.consumableType = es.uvigo.esei.infraestructura.entities.ConsumableType.TRANSFER_KIT"), })
public class Consumable {

	@Id
	@JoinColumn
	@Column(name = "consumableName", length = 100)
	private String consumableName;

	@Column(length = 12, nullable = false)
	@Enumerated(EnumType.STRING)
	private ConsumableType consumableType;

	@Column(length = 45)
	private String colour;

	@Column(length = 1000, nullable = false)
	private String description;

	@ManyToMany(mappedBy = "consumables")
	private List<Model> models;

	@ManyToMany(mappedBy = "consumables")
	private List<Printer> printers;
	
	// Constructor required for JPA framework
	Consumable() {
	}

	public Consumable(String consumableName, String consumableType, String colour, String description) {
		this.colour = colour;
		build(consumableName, consumableType, description);
	}

	public Consumable(String consumableName, String consumableType, String description) {
		build(consumableName, consumableType, description);
	}

	private void build(String consumableName, String consumableType, String description) {
		this.consumableName = consumableName;
		this.description = description;
		switch (consumableType) {
		case "Cartucho":
			this.consumableType = ConsumableType.CARTRIDGE;
			break;
		case "Toner":
			this.consumableType = ConsumableType.TONER;
			break;
		case "Tambor":
			this.consumableType = ConsumableType.DRUM;
			break;
		case "Kit de transferencia":
			this.consumableType = ConsumableType.TRANSFER_KIT;
			break;
		case "Cinturón de arrastre":
			this.consumableType = ConsumableType.BELT_UNIT;
			break;
		case "Fusor":
			this.consumableType = ConsumableType.FUSER;
			break;
		case "Recipiente de residuos":
			this.consumableType = ConsumableType.GARBAGE_UNIT;
			break;
		}
	}

	public String getConsumableName() {
		return consumableName;
	}

	public void setConsumableName(String consumableName) {
		this.consumableName = consumableName;
	}

	public ConsumableType getConsumableType() {
		return consumableType;
	}

	public void setConsumableType(ConsumableType consumableType) {
		this.consumableType = consumableType;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Model> getModels() {
		return models;
	}

	public void setModels(List<Model> models) {
		this.models = models;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colour == null) ? 0 : colour.hashCode());
		result = prime * result + ((consumableName == null) ? 0 : consumableName.hashCode());
		result = prime * result + ((consumableType == null) ? 0 : consumableType.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((models == null) ? 0 : models.hashCode());
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
		Consumable other = (Consumable) obj;
		if (colour == null) {
			if (other.colour != null)
				return false;
		} else if (!colour.equals(other.colour))
			return false;
		if (consumableName == null) {
			if (other.consumableName != null)
				return false;
		} else if (!consumableName.equals(other.consumableName))
			return false;
		if (consumableType != other.consumableType)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (models == null) {
			if (other.models != null)
				return false;
		} else if (!models.equals(other.models))
			return false;
		return true;
	}
	
	
}
