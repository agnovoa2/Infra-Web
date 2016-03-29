package es.uvigo.esei.infraestructura.controller;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.ejb.ConsumableEJB;
import es.uvigo.esei.infraestructura.ejb.ModelEJB;
import es.uvigo.esei.infraestructura.entities.Consumable;
import es.uvigo.esei.infraestructura.entities.Model;

@ViewScoped
@ManagedBean(name = "addPrinterModel")
public class AddPrinterModelController {

	@Inject
	private ConsumableEJB consumableEJB;
	
	@Inject
	private ModelEJB modelEJB;

	private String consumableName;
	private String consumableDescription;
	private String consumableColor;
	private String consumableType;
	private List<Consumable> blackConsumables;
	private List<Consumable> photoBlackConsumables;
	private List<Consumable> magentaConsumables;
	private List<Consumable> lightMagentaConsumables;
	private List<Consumable> cyanConsumables;
	private List<Consumable> lightCyanConsumables;
	private List<Consumable> yellowConsumables;
	private List<Consumable> tricolorConsumables;
	private List<Consumable> garbageUnits;
	private List<Consumable> drumUnits;
	private List<Consumable> transferKitUnits;
	private List<Consumable> beltUnits;
	private List<Consumable> fuserUnits;
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
		updateSelectOnMenus();
    }
	
	public void update(AjaxBehaviorEvent evt) {
	    updateSelectOnMenus();
	}
	
	public void updateSelectOnMenus(){
		blackConsumables = consumableEJB.findAllBlackConsumables();
		photoBlackConsumables = consumableEJB.findAllPhotoBlackConsumables();
		magentaConsumables = consumableEJB.findAllMagentaConsumables();
		lightMagentaConsumables = consumableEJB.findAllLightMagentaConsumables();
		cyanConsumables = consumableEJB.findAllCyanConsumables();
		lightCyanConsumables = consumableEJB.findAllLightCyanConsumables();
		yellowConsumables = consumableEJB.findAllYellowConsumables();
		tricolorConsumables = consumableEJB.findAllTricolorConsumables();
		garbageUnits = consumableEJB.findAllGarbageUnitConsumables();
		drumUnits = consumableEJB.findAllDrumConsumables();
		transferKitUnits = consumableEJB.findAllTransferKitConsumables();
		beltUnits = consumableEJB.findAllBeltUnitConsumables();
		fuserUnits = consumableEJB.findAllFuserConsumables();
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

	public void doAddConsumable() {
		if (getConsumableType().equals("Cartucho") || getConsumableType().equals("Toner")) {
			consumableEJB.addConsumable(new Consumable(getConsumableName(), getConsumableType(), getConsumableColor(),
					getConsumableDescription()));
		} else {
			consumableEJB.addConsumable(
					new Consumable(getConsumableName(), getConsumableType(), getConsumableDescription()));
		}
		updateSelectOnMenus();
	}

	public void doAddModel(){
		Model model = new Model(getModelName(),getTradeMark());
		List<Consumable> consumables = fillConsumableList();
		model.setConsumables(consumables);
		modelEJB.addModel(model);
	}
	
	public List<Consumable> fillConsumableList(){ 
		
		List<Consumable> consumables= new LinkedList<Consumable>();
		if(!blackConsumable.equals("N/A"))
			consumables.add(consumableEJB.find(blackConsumable));
		if(!photoBlackConsumable.equals("N/A"))
			consumables.add(consumableEJB.find(photoBlackConsumable));
		if(!yellowConsumable.equals("N/A"))
			consumables.add(consumableEJB.find(yellowConsumable));
		if(!magentaConsumable.equals("N/A"))
			consumables.add(consumableEJB.find(magentaConsumable));
		if(!lightMagentaConsumable.equals("N/A"))
			consumables.add(consumableEJB.find(lightMagentaConsumable));
		if(!cyanConsumable.equals("N/A"))
			consumables.add(consumableEJB.find(cyanConsumable));
		if(!lightCyanConsumable.equals("N/A"))
			consumables.add(consumableEJB.find(lightCyanConsumable));
		if(!tricolorConsumable.equals("N/A"))
			consumables.add(consumableEJB.find(tricolorConsumable));
		if(!garbageUnit.equals("N/A"))
			consumables.add(consumableEJB.find(garbageUnit));
		if(!drum.equals("N/A"))
			consumables.add(consumableEJB.find(drum));
		if(!transferKit.equals("N/A"))
			consumables.add(consumableEJB.find(transferKit));
		if(!beltUnit.equals("N/A"))
			consumables.add(consumableEJB.find(beltUnit));
		if(!fuser.equals("N/A"))
			consumables.add(consumableEJB.find(fuser));
		if(!modelName.equals("N/A"))
			consumables.add(consumableEJB.find(modelName));
		if(!tradeMark.equals("N/A"))
			consumables.add(consumableEJB.find(tradeMark));
		
		return consumables;
	}
	
	public List<Consumable> getBlackConsumables() {
		return blackConsumables;
	}

	public void setBlackConsumables(List<Consumable> blackConsumables) {
		this.blackConsumables = blackConsumables;
	}

	public List<Consumable> getPhotoBlackConsumables() {
		return photoBlackConsumables;
	}

	public void setPhotoBlackConsumables(List<Consumable> photoBlackConsumables) {
		this.photoBlackConsumables = photoBlackConsumables;
	}

	public List<Consumable> getMagentaConsumables() {
		return magentaConsumables;
	}

	public void setMagentaConsumables(List<Consumable> magentaConsumables) {
		this.magentaConsumables = magentaConsumables;
	}

	public List<Consumable> getLightMagentaConsumables() {
		return lightMagentaConsumables;
	}

	public void setLightMagentaConsumables(List<Consumable> lightMagentaConsumables) {
		this.lightMagentaConsumables = lightMagentaConsumables;
	}

	public List<Consumable> getCyanConsumables() {
		return cyanConsumables;
	}

	public void setCyanConsumables(List<Consumable> cyanConsumables) {
		this.cyanConsumables = cyanConsumables;
	}

	public List<Consumable> getLightCyanConsumables() {
		return lightCyanConsumables;
	}

	public void setLightCyanConsumables(List<Consumable> lightCyanConsumables) {
		this.lightCyanConsumables = lightCyanConsumables;
	}

	public List<Consumable> getYellowConsumables() {
		return yellowConsumables;
	}

	public void setYellowConsumables(List<Consumable> yellowConsumables) {
		this.yellowConsumables = yellowConsumables;
	}

	public List<Consumable> getTricolorConsumables() {
		return tricolorConsumables;
	}

	public void setTricolorConsumables(List<Consumable> tricolorConsumables) {
		this.tricolorConsumables = tricolorConsumables;
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
		return garbageUnits;
	}

	public void setGarbageUnits(List<Consumable> garbageUnits) {
		this.garbageUnits = garbageUnits;
	}

	public List<Consumable> getDrumUnits() {
		return drumUnits;
	}

	public void setDrumUnits(List<Consumable> drumUnits) {
		this.drumUnits = drumUnits;
	}

	public List<Consumable> getTransferKitUnits() {
		return transferKitUnits;
	}

	public void setTransferKitUnits(List<Consumable> transferKitUnits) {
		this.transferKitUnits = transferKitUnits;
	}

	public List<Consumable> getBeltUnits() {
		return beltUnits;
	}

	public void setBeltUnits(List<Consumable> beltUnits) {
		this.beltUnits = beltUnits;
	}

	public List<Consumable> getFuserUnits() {
		return fuserUnits;
	}

	public void setFuserUnits(List<Consumable> fuserUnits) {
		this.fuserUnits = fuserUnits;
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
