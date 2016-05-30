package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Consumable;
import es.uvigo.esei.infraestructura.entities.Model;
import es.uvigo.esei.infraestructura.entities.Printer;
import es.uvigo.esei.infraestructura.entities.User;
import es.uvigo.esei.infraestructura.facade.ConsumableGatewayBean;
import es.uvigo.esei.infraestructura.facade.ModelGatewayBean;
import es.uvigo.esei.infraestructura.facade.PrinterGatewayBean;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;

@ViewScoped
@ManagedBean(name = "modelManagement")
public class ModelManagementController {

	@Inject
	private ModelGatewayBean modelGateway;

	@Inject
	private PrinterGatewayBean printerGateway;

	@Inject
	private UserGatewayBean userGateway;

	@Inject
	private ConsumableGatewayBean consumableGateway;

	private String newModelName;
	private String modelName;
	private String tradeMark;
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
	private String message;
	private boolean editable = false;
	private boolean error = false;

	public void doAddModel() throws IOException {
		try {
			Model model = new Model(getModelName(), getTradeMark());
			List<Consumable> consumables = fillConsumableList();
			if (consumables == null) {
				throw new Exception("El modelo debe contener algún consumible.");
			} else {
				model.setConsumables(consumables);
				modelGateway.create(model);
				modelGateway.save();
			}
			error = false;
		} catch (Exception e) {
			message = e.getMessage();
			error = true;
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

	public void doRemoveModel(String modelName) {

		modelGateway.find(modelName);
		if (modelGateway.getCurrent() != null) {
			if (modelGateway.getCurrent().getPrinters() != null) {
				for (Printer printer : modelGateway.getCurrent().getPrinters()) {
					printerGateway.find(printer.getInventoryNumber());
					printerGateway.getCurrent().setUnused(true);
					for (User user : printerGateway.getCurrent().getUsers()) {
						userGateway.find(user.getLogin());
						userGateway.getCurrent().getPrinters().remove(printer);
						userGateway.save();
					}
					printerGateway.save();
				}
			}
		}
		modelGateway.getCurrent().setUnused(true);
		modelGateway.save();
	}

	public void doSetEditModel(String modelName) {

		newModelName = modelName;
		modelGateway.find(modelName);
		setModelName(modelGateway.getCurrent().getModelName());
		setTradeMark(modelGateway.getCurrent().getTradeMark());
		for (Consumable consumable : modelGateway.getCurrent().getConsumables()) {
			switch (consumable.getConsumableType()) {
			case BELT_UNIT:
				beltUnit = consumable.getConsumableName();
				break;
			case DRUM:
				drum = consumable.getConsumableName();
				break;
			case FUSER:
				fuser = consumable.getConsumableName();
				break;
			case GARBAGE_UNIT:
				garbageUnit = consumable.getConsumableName();
				break;
			case TRANSFER_KIT:
				transferKit = consumable.getConsumableName();
				break;
			case CARTRIDGE:
			case TONER:
				switch (consumable.getColour()) {
				case "Negro":
					blackConsumable = consumable.getConsumableName();
					break;
				case "Negro fotográfico":
					photoBlackConsumable = consumable.getConsumableName();
					break;
				case "Tricolor":
					tricolorConsumable = consumable.getConsumableName();
					break;
				case "Cyan":
					cyanConsumable = consumable.getConsumableName();
					break;
				case "Cyan claro":
					lightCyanConsumable = consumable.getConsumableName();
					break;
				case "Magenta":
					magentaConsumable = consumable.getConsumableName();
					break;
				case "Magenta claro":
					lightMagentaConsumable = consumable.getConsumableName();
					break;
				case "Amarillo":
					yellowConsumable = consumable.getConsumableName();
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		}
		editable = true;
	}

	public void doEditModel() throws IOException {
		try {
			modelGateway.find(modelName);
			modelGateway.getCurrent().setModelName(newModelName);
			modelGateway.getCurrent().setTradeMark(tradeMark);
			List<Consumable> consumables = fillConsumableList();
			modelGateway.getCurrent().setConsumables(consumables);
			modelGateway.save();
			blackConsumable = "";
			photoBlackConsumable = "";
			yellowConsumable = "";
			magentaConsumable = "";
			lightMagentaConsumable = "";
			cyanConsumable = "";
			lightCyanConsumable = "";
			tricolorConsumable = "";
			garbageUnit = "";
			drum = "";
			transferKit = "";
			beltUnit = "";
			fuser = "";
			error = false;
			editable = false;
		} catch (Exception e) {
			message = "Ya existe un modelo con ese nombre en la base de datos.";
			error = true;
		}
	}

	public String getNewModelName() {
		return newModelName;
	}

	public void setNewModelName(String newModelName) {
		this.newModelName = newModelName;
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

	public List<Model> getAllModel() {
		return modelGateway.getAll();
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
}
