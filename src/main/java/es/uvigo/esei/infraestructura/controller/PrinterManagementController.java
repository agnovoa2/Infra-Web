package es.uvigo.esei.infraestructura.controller;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Model;
import es.uvigo.esei.infraestructura.entities.Printer;
import es.uvigo.esei.infraestructura.entities.User;
import es.uvigo.esei.infraestructura.facade.ModelGatewayBean;
import es.uvigo.esei.infraestructura.facade.PrinterGatewayBean;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;

@ViewScoped
@ManagedBean(name = "printerManagement")
public class PrinterManagementController {

	@Inject
	private PrinterGatewayBean printerGateway;

	@Inject
	private ModelGatewayBean modelGateway;
	
	@Inject
	private UserGatewayBean userGateway;

	private String newUbication;
	private String ubication;
	private int inventoryNumber;
	private String model;
	private String newModel;

	public void doAddPrinter() {
		this.printerGateway.create(new Printer(getInventoryNumber(),getUbication()));
		this.printerGateway.getCurrent().setModel(modelGateway.find(getModel()));
		this.printerGateway.save();
	}

	public void doRemovePrinter(int inventoryNumber) {

		printerGateway.find(inventoryNumber);
		
		if(printerGateway.getCurrent() != null){
			if(printerGateway.getCurrent().getUsers() != null){
				for(User user : printerGateway.getCurrent().getUsers()){
					userGateway.find(user.getLogin());
					userGateway.getCurrent().getPrinters().remove(printerGateway.getCurrent());
					userGateway.save();
				}
			}
			printerGateway.getCurrent().setUsers(null);
		}
		printerGateway.getCurrent().setUnused(true);
		printerGateway.save();
	}

	public void doSetEditPrinter(int inventoryNumber) {
		
		this.inventoryNumber = inventoryNumber;
		printerGateway.find(inventoryNumber);
		setNewModel(printerGateway.getCurrent().getModel().getModelName());
		setNewUbication(printerGateway.getCurrent().getUbication());
	}

	public void doEditPrinter() {
		
		printerGateway.find(inventoryNumber);
		printerGateway.getCurrent().setUbication(getNewUbication());
		printerGateway.getCurrent().setModel(modelGateway.find(getNewModel()));
		printerGateway.save();		
	}

	public List<Printer> getAllPrinter() {
		return printerGateway.getAll();
	}
	
	public List<Model> getAllModel() {
		return modelGateway.getAll();
	}

	public String getNewUbication() {
		return newUbication;
	}

	public void setNewUbication(String newUbication) {
		this.newUbication = newUbication;
	}

	public String getUbication() {
		return ubication;
	}

	public void setUbication(String ubication) {
		this.ubication = ubication;
	}

	public int getInventoryNumber() {
		return inventoryNumber;
	}

	public void setInventoryNumber(int inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getNewModel() {
		return newModel;
	}

	public void setNewModel(String newModel) {
		this.newModel = newModel;
	}

	
}

