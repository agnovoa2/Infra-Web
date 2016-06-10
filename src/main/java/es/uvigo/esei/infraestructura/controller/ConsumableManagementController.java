package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Consumable;
import es.uvigo.esei.infraestructura.entities.ConsumableType;
import es.uvigo.esei.infraestructura.entities.Model;
import es.uvigo.esei.infraestructura.facade.ConsumableGatewayBean;
import es.uvigo.esei.infraestructura.facade.ModelGatewayBean;

@ViewScoped
@ManagedBean(name = "consumableManagement")
public class ConsumableManagementController {

	@Inject
	private ModelGatewayBean modelGateway;

	@Inject
	private ConsumableGatewayBean consumableGateway;

	private String newConsumableName;
	private String consumableName;
	private String description;
	private String type;
	private String color;
	private String message;
	private boolean editable = false;
	private boolean error = false;

	public void doAddConsumable() throws IOException {
		try {
			if (type.equals("Toner") || type.equals("Cartucho"))
				consumableGateway.create(new Consumable(consumableName, type, color, description));
			else {
				consumableGateway.create(new Consumable(consumableName, type, description));
			}
			consumableGateway.save();
			error = false;
		} catch (SQLException e) {
			message = e.getMessage();
			error = true;
		}
	}

	public void doRemoveConsumable(String consumable) {

		consumableGateway.find(consumable);
		if (consumableGateway.getCurrent() != null) {
			if (consumableGateway.getCurrent().getModels() != null) {
				for (Model model : consumableGateway.getCurrent().getModels()) {
					modelGateway.find(model.getModelName());
					modelGateway.getCurrent().getConsumables().remove(consumableGateway.getCurrent());
					modelGateway.save();
				}
			}
			consumableGateway.getCurrent().setModels(null);
		}
		consumableGateway.remove(consumableGateway.getCurrent().getId());
		consumableGateway.save();
	}

	public void doSetEditConsumable(String consumable) {

		newConsumableName = consumable;
		consumableGateway.find(consumable);
		setConsumableName(consumableGateway.getCurrent().getConsumableName());
		setType(consumableGateway.getCurrent().getConsumableType().toString());
		setDescription(consumableGateway.getCurrent().getDescription());
		if (type.equals(ConsumableType.TONER) || type.equals(ConsumableType.CARTRIDGE))
			setColor(consumableGateway.getCurrent().getColour());
		editable = true;
	}

	public void doEditConsumable() {
		try {
			if (editable) {
				consumableGateway.find(consumableName);
				consumableGateway.getCurrent().setConsumableName(newConsumableName);
				consumableGateway.getCurrent().setDescription(description);
				consumableGateway.getCurrent().setConsumableType(type);
				if (consumableGateway.getCurrent().getConsumableType().equals(ConsumableType.TONER) || type.equals(ConsumableType.CARTRIDGE)) {
					consumableGateway.getCurrent().setColour(color);
				} else {
					consumableGateway.getCurrent().setColour("");
				}
				consumableGateway.save();
				description = "";
				consumableName = "";
				newConsumableName = "";
			}
			editable = false;
			error = false;
		} catch (Exception e) {
			message = "Ya existe un consumible en la base de datos con ese nombre.";
			error = true;
		}
	}

	public String getConsumableName() {
		return consumableName;
	}

	public void setConsumableName(String consumableName) {
		this.consumableName = consumableName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<Consumable> getAllConsumable() {
		return consumableGateway.findAllConsumables();
	}

	public String getNewConsumableName() {
		return newConsumableName;
	}

	public void setNewConsumableName(String newConsumableName) {
		this.newConsumableName = newConsumableName;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
}
