package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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

	private ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

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
				this.consumableGateway.create(new Consumable(getConsumableName(), getConsumableType(),
						getConsumableColor(), getConsumableDescription()));
			} else {
				this.consumableGateway
						.create(new Consumable(getConsumableName(), getConsumableType(), getConsumableDescription()));
			}
			this.consumableGateway.save();
			this.consumableName = "";
			this.consumableDescription = "";
		} catch (SQLException e) {
			context.redirect("addPrinterModel.xhtml?error=true");
		}
	}

	public void doAddModel() throws IOException {
		try {
			Model model = new Model(getModelName(), getTradeMark());
			List<Consumable> consumables = fillConsumableList();
			if (consumables == null) {
				context.redirect("addPrinterModel.xhtml?error=true");
			} else {
				model.setConsumables(consumables);
				this.modelGateway.create(model);
				this.modelGateway.save();
			}
		} catch (SQLException e) {
			context.redirect("addPrinterModel.xhtml?error=true");
		}
	}

	private List<Consumable> fillConsumableList() {
		boolean empty = true;
		List<Consumable> consumables = new LinkedList<Consumable>();
		if (!blackConsumable.equals("N/A")) {
			consumables.add(this.consumableGateway.find(blackConsumable));
			empty = false;
		}
		if (!photoBlackConsumable.equals("N/A")) {
			consumables.add(this.consumableGateway.find(photoBlackConsumable));
			empty = false;
		}
		if (!yellowConsumable.equals("N/A")) {
			consumables.add(this.consumableGateway.find(yellowConsumable));
			empty = false;
		}
		if (!magentaConsumable.equals("N/A")) {
			consumables.add(this.consumableGateway.find(magentaConsumable));
			empty = false;
		}
		if (!lightMagentaConsumable.equals("N/A")) {
			consumables.add(this.consumableGateway.find(lightMagentaConsumable));
			empty = false;
		}
		if (!cyanConsumable.equals("N/A")) {
			consumables.add(this.consumableGateway.find(cyanConsumable));
			empty = false;
		}
		if (!lightCyanConsumable.equals("N/A")) {
			consumables.add(this.consumableGateway.find(lightCyanConsumable));
			empty = false;
		}
		if (!tricolorConsumable.equals("N/A")) {
			consumables.add(this.consumableGateway.find(tricolorConsumable));
			empty = false;
		}
		if (!garbageUnit.equals("N/A")) {
			consumables.add(this.consumableGateway.find(garbageUnit));
			empty = false;
		}
		if (!drum.equals("N/A")) {
			consumables.add(this.consumableGateway.find(drum));
			empty = false;
		}
		if (!transferKit.equals("N/A")) {
			consumables.add(this.consumableGateway.find(transferKit));
			empty = false;
		}
		if (!beltUnit.equals("N/A")) {
			consumables.add(this.consumableGateway.find(beltUnit));
			empty = false;
		}
		if (!fuser.equals("N/A")) {
			consumables.add(this.consumableGateway.find(fuser));
			empty = false;
		}

		if (empty == true) {
			return null;
		}
		return consumables;
	}

	public List<Consumable> getBlackConsumables() {
		return this.consumableGateway.findAllBlackConsumables();
	}

	public List<Consumable> getPhotoBlackConsumables() {
		return this.consumableGateway.findAllPhotoBlackConsumables();
	}

	public List<Consumable> getMagentaConsumables() {
		return this.consumableGateway.findAllMagentaConsumables();
	}

	public List<Consumable> getLightMagentaConsumables() {
		return this.consumableGateway.findAllLightMagentaConsumables();
	}

	public List<Consumable> getCyanConsumables() {
		return this.consumableGateway.findAllCyanConsumables();
	}

	public List<Consumable> getLightCyanConsumables() {
		return this.consumableGateway.findAllLightCyanConsumables();
	}

	public List<Consumable> getYellowConsumables() {
		return this.consumableGateway.findAllYellowConsumables();
	}

	public List<Consumable> getTricolorConsumables() {
		return this.consumableGateway.findAllTricolorConsumables();
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
		return this.consumableGateway.findAllGarbageUnitConsumables();
	}

	public List<Consumable> getDrumUnits() {
		return this.consumableGateway.findAllDrumConsumables();
	}

	public List<Consumable> getTransferKitUnits() {
		return this.consumableGateway.findAllTransferKitConsumables();
	}

	public List<Consumable> getBeltUnits() {
		return this.consumableGateway.findAllBeltUnitConsumables();
	}

	public List<Consumable> getFuserUnits() {
		return this.consumableGateway.findAllFuserConsumables();
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
}
