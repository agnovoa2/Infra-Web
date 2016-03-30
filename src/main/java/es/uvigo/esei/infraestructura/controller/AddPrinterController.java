package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Model;
import es.uvigo.esei.infraestructura.entities.Printer;
import es.uvigo.esei.infraestructura.facade.ModelGatewayBean;
import es.uvigo.esei.infraestructura.facade.PrinterGatewayBean;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;

@RequestScoped
@ManagedBean(name = "addPrinter")
public class AddPrinterController {
	
	@Inject
	private Principal currentUser;
	
	@Inject 
	private PrinterGatewayBean printerGateway;
	
	@Inject
	private ModelGatewayBean modelGateway;
	
	@Inject
	private UserGatewayBean userGatewayBean;
	
	private ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	
	private String model;
	private String ubication;
	private int inventoryNumber;
	
	@PostConstruct
    public void init() {
		this.userGatewayBean.find(currentUser.getName());
    }
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public List<Model> getAllModels() {
		return this.modelGateway.getAll();
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
	
	public void doAddPrinter() throws IOException{
		this.printerGateway.create(new Printer(getInventoryNumber(),getUbication()));
		this.printerGateway.getCurrent().setModel(modelGateway.find(getModel()));
		this.printerGateway.save();
		
		this.userGatewayBean.getCurrent().getPrinters().add(this.printerGateway.getCurrent());
		this.userGatewayBean.save();
		context.redirect("printerList.xhtml");
	}
}
