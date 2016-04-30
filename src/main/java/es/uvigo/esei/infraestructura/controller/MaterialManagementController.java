package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Material;
import es.uvigo.esei.infraestructura.facade.MaterialGatewayBean;

@ViewScoped
@ManagedBean(name = "materialManagement")
public class MaterialManagementController {

	@Inject
	private MaterialGatewayBean materialGateway;

	private int id;
	private String materialName;
	private String materialType;
	private String material;
	private String capacity;
	private String tradeMark;
	private String model;
	private String speed;
	private String destination;
	private String size;
	private String resolution;
	private String proportion;
	private int quantity;

	public void doAddMonitor() {
		materialGateway
				.create(new Material(materialName, size, proportion, resolution, materialType, tradeMark, model));
		materialGateway.save();
	}

	public void doAddHardDrive() {
		materialGateway.create(new Material(materialName, materialType, capacity, tradeMark, model));
		materialGateway.save();
	}

	public void doAddMemory() {
		materialGateway.create(new Material(materialName, materialType, capacity, speed, destination, tradeMark));
		materialGateway.save();
	}

	public void doAddOther() {
		materialGateway.create(new Material(materialName, materialType, capacity, tradeMark, model, speed, destination,
				size, resolution, proportion));
		materialGateway.save();
	}

	public void doRemoveMaterial(int id) {

		materialGateway.find(id);

		materialGateway.getCurrent().setUnused(true);
		materialGateway.save();
	}

	public void doSetEdit(int id) {

		this.id = id;
		materialGateway.find(id);
		materialName = materialGateway.getCurrent().getMaterialName();
		size = materialGateway.getCurrent().getSize();
		proportion = materialGateway.getCurrent().getProportion();
		resolution = materialGateway.getCurrent().getResolution();
		materialType = materialGateway.getCurrent().getMaterialType();
		tradeMark = materialGateway.getCurrent().getTradeMark();
		model = materialGateway.getCurrent().getModel();
		quantity = materialGateway.getCurrent().getQuantity();
		capacity = materialGateway.getCurrent().getCapacity();
		speed = materialGateway.getCurrent().getSpeed();
		destination = materialGateway.getCurrent().getDestination();
	}

	public void doEditMaterial() {

		materialGateway.find(id);
		materialGateway.getCurrent().setMaterialName(materialName);
		materialGateway.getCurrent().setSize(size);
		materialGateway.getCurrent().setProportion(proportion);
		materialGateway.getCurrent().setResolution(resolution);
		materialGateway.getCurrent().setMaterialType(materialType);
		materialGateway.getCurrent().setTradeMark(tradeMark);
		materialGateway.getCurrent().setModel(model);
		materialGateway.getCurrent().setQuantity(quantity);
		materialGateway.getCurrent().setCapacity(capacity);
		materialGateway.getCurrent().setSpeed(speed);
		materialGateway.getCurrent().setDestination(destination);
		materialGateway.save();
	}

	public List<Material> getAllMaterial(){
		switch (material) {
		case "monitor":
			return materialGateway.getAllMonitors();
		case "ram":
			return materialGateway.getAllRamMemories();
		case "disco duro":
			return materialGateway.getAllHardDrives();
		case "otros":
			return materialGateway.getAllOthers();
		default:
			return null;
		}
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getTradeMark() {
		return tradeMark;
	}

	public void setTradeMark(String tradeMark) {
		this.tradeMark = tradeMark;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getProportion() {
		return proportion;
	}

	public void setProportion(String proportion) {
		this.proportion = proportion;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void redirectIfNotMaterial() throws IOException {
		if (this.material != null && !this.material.toLowerCase().equals("monitor")
				&& !this.material.toLowerCase().equals("disco duro") && !this.material.toLowerCase().equals("ram")
				&& !this.material.toLowerCase().equals("otros")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("materialManagement.xhtml");
		}
	}

}
