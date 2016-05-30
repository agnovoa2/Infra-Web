package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Consumable;
import es.uvigo.esei.infraestructura.entities.Model;
import es.uvigo.esei.infraestructura.facade.ConsumableGatewayBean;
import es.uvigo.esei.infraestructura.facade.ModelGatewayBean;

@ViewScoped
@ManagedBean(name = "addPrinterModel")
public class AddPrinterModelController {

	@Inject
	private ConsumableGatewayBean consumableGateway;

	@Inject
	private ModelGatewayBean modelGateway;

	private String consumableName;
	private String consumableDescription;
	private String consumableColor;
	private String consumableType;
	private String blackConsumable;
	private String photoBlackConsumable;
	private String yellowConsumable;
	private String magentaConsumable;
	private String lightMagentaConsumable;
	private String cyanConsumable;
	private String lightCyanConsumable;
	private String tricolorConsumable;
	private String garbageUnit;
	private String drum;
	private String transferKit;
	private String beltUnit;
	private String fuser;
	private String modelName;
	private String tradeMark;
	private boolean success = false;
	private boolean error = false;
	private String message;

	@PostConstruct
	public void init() {

	}

	public String getConsumableName() {
		return consumableName;
	}

	public void setConsumableName(String consumableName) {
		this.consumableName = consumableName;
	}

	public String getConsumableDescription() {
		return consumableDescription;
	}

	public void setConsumableDescription(String consumableDescription) {
		this.consumableDescription = consumableDescription;
	}

	public String getConsumableColor() {
		return consumableColor;
	}

	public void setConsumableColor(String consumableColor) {
		this.consumableColor = consumableColor;
	}

	public String getConsumableType() {
		return consumableType;
	}

	public void setConsumableType(String consumableType) {
		this.consumableType = consumableType;
	}

	public void doAddConsumable() throws IOException {
		try {
			if (getConsumableType().equals("Cartucho") || getConsumableType().equals("Toner")) {
				consumableGateway.create(new Consumable(getConsumableName(), getConsumableType(),
						getConsumableColor(), getConsumableDescription()));
			} else {
				consumableGateway
						.create(new Consumable(getConsumableName(), getConsumableType(), getConsumableDescription()));
			}
			consumableGateway.save();
			consumableName = "";
			consumableDescription = "";
			success = true;
			error = false;
			message = "Consumible añadido correctamente";
		} catch (SQLException e) {
			success = false;
			error = true;
			message = e.getMessage();
		}
	}

	public void doAddModel() throws IOException {
		try {
			Model model = new Model(getModelName(), getTradeMark());
			List<Consumable> consumables = fillConsumableList();
			if (consumables == null) {
				throw new SQLException("El modelo debe contener algún consumible.");
			} else {
				model.setConsumables(consumables);
				modelGateway.create(model);
				modelGateway.save();
			}
			success = true;
			error = false;
			message = "Modelo añadido correctamente";
		} catch (SQLException e) {
			success = false;
			error = true;
			message = e.getMessage();
		}
	}

	private List<Consumable> fillConsumableList() {
		boolean empty = true;
		List<Consumable> consumables = new LinkedList<Consumable>();
		if (!blackConsumable.equals("N/A")) {
			consumables.add(consumableGateway.find(blackConsumable));
			empty = false;
		}
		if (!photoBlackConsumable.equals("N/A")) {
			consumables.add(consumableGateway.find(photoBlackConsumable));
			empty = false;
		}
		if (!yellowConsumable.equals("N/A")) {
			consumables.add(consumableGateway.find(yellowConsumable));
			empty = false;
		}
		if (!magentaConsumable.equals("N/A")) {
			consumables.add(consumableGateway.find(magentaConsumable));
			empty = false;
		}
		if (!lightMagentaConsumable.equals("N/A")) {
			consumables.add(consumableGateway.find(lightMagentaConsumable));
			empty = false;
		}
		if (!cyanConsumable.equals("N/A")) {
			consumables.add(consumableGateway.find(cyanConsumable));
			empty = false;
		}
		if (!lightCyanConsumable.equals("N/A")) {
			consumables.add(consumableGateway.find(lightCyanConsumable));
			empty = false;
		}
		if (!tricolorConsumable.equals("N/A")) {
			consumables.add(consumableGateway.find(tricolorConsumable));
			empty = false;
		}
		if (!garbageUnit.equals("N/A")) {
			consumables.add(consumableGateway.find(garbageUnit));
			empty = false;
		}
		if (!drum.equals("N/A")) {
			consumables.add(consumableGateway.find(drum));
			empty = false;
		}
		if (!transferKit.equals("N/A")) {
			consumables.add(consumableGateway.find(transferKit));
			empty = false;
		}
		if (!beltUnit.equals("N/A")) {
			consumables.add(consumableGateway.find(beltUnit));
			empty = false;
		}
		if (!fuser.equals("N/A")) {
			consumables.add(consumableGateway.find(fuser));
			empty = false;
		}

		if (empty == true) {
			return null;
		}
		return consumables;
	}

	public List<Consumable> getBlackConsumables() {
		return consumableGateway.findAllBlackConsumables();
	}

	public List<Consumable> getPhotoBlackConsumables() {
		return consumableGateway.findAllPhotoBlackConsumables();
	}

	public List<Consumable> getMagentaConsumables() {
		return consumableGateway.findAllMagentaConsumables();
	}

	public List<Consumable> getLightMagentaConsumables() {
		return consumableGateway.findAllLightMagentaConsumables();
	}

	public List<Consumable> getCyanConsumables() {
		return consumableGateway.findAllCyanConsumables();
	}

	public List<Consumable> getLightCyanConsumables() {
		return consumableGateway.findAllLightCyanConsumables();
	}

	public List<Consumable> getYellowConsumables() {
		return consumableGateway.findAllYellowConsumables();
	}

	public List<Consumable> getTricolorConsumables() {
		return consumableGateway.findAllTricolorConsumables();
	}

	public String getBlackConsumable() {
		return blackConsumable;
	}

	public void setBlackConsumable(String blackConsumable) {
		this.blackConsumable = blackConsumable;
	}

	public String getPhotoBlackConsumable() {
		return photoBlackConsumable;
	}

	public void setPhotoBlackConsumable(String photoBlackConsumable) {
		this.photoBlackConsumable = photoBlackConsumable;
	}

	public String getYellowConsumable() {
		return yellowConsumable;
	}

	public void setYellowConsumable(String yellowConsumable) {
		this.yellowConsumable = yellowConsumable;
	}

	public String getMagentaConsumable() {
		return magentaConsumable;
	}

	public void setMagentaConsumable(String magentaConsumable) {
		this.magentaConsumable = magentaConsumable;
	}

	public String getLightMagentaConsumable() {
		return lightMagentaConsumable;
	}

	public void setLightMagentaConsumable(String lightMagentaConsumable) {
		this.lightMagentaConsumable = lightMagentaConsumable;
	}

	public String getCyanConsumable() {
		return cyanConsumable;
	}

	public void setCyanConsumable(String cyanConsumable) {
		this.cyanConsumable = cyanConsumable;
	}

	public String getLightCyanConsumable() {
		return lightCyanConsumable;
	}

	public void setLightCyanConsumable(String lightCyanConsumable) {
		this.lightCyanConsumable = lightCyanConsumable;
	}

	public String getTricolorConsumable() {
		return tricolorConsumable;
	}

	public void setTricolorConsumable(String tricolorConsumable) {
		this.tricolorConsumable = tricolorConsumable;
	}

	public List<Consumable> getGarbageUnits() {
		return consumableGateway.findAllGarbageUnitConsumables();
	}

	public List<Consumable> getDrumUnits() {
		return consumableGateway.findAllDrumConsumables();
	}

	public List<Consumable> getTransferKitUnits() {
		return consumableGateway.findAllTransferKitConsumables();
	}

	public List<Consumable> getBeltUnits() {
		return consumableGateway.findAllBeltUnitConsumables();
	}

	public List<Consumable> getFuserUnits() {
		return consumableGateway.findAllFuserConsumables();
	}

	public String getGarbageUnit() {
		return garbageUnit;
	}

	public void setGarbageUnit(String garbageUnit) {
		this.garbageUnit = garbageUnit;
	}

	public String getDrum() {
		return drum;
	}

	public void setDrum(String drum) {
		this.drum = drum;
	}

	public String getTransferKit() {
		return transferKit;
	}

	public void setTransferKit(String transferKit) {
		this.transferKit = transferKit;
	}

	public String getBeltUnit() {
		return beltUnit;
	}

	public void setBeltUnit(String beltUnit) {
		this.beltUnit = beltUnit;
	}

	public String getFuser() {
		return fuser;
	}

	public void setFuser(String fuser) {
		this.fuser = fuser;
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

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
