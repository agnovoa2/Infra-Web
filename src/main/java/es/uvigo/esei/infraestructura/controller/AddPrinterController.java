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

import es.uvigo.esei.infraestructura.ejb.ModelEJB;
import es.uvigo.esei.infraestructura.ejb.PrinterEJB;
import es.uvigo.esei.infraestructura.ejb.UserEJB;
import es.uvigo.esei.infraestructura.entities.Model;
import es.uvigo.esei.infraestructura.entities.Printer;

@RequestScoped
@ManagedBean(name = "addPrinter")
public class AddPrinterController {
	
	@Inject
	private Principal currentUser;
	
	@Inject 
	private PrinterEJB printerEJB;
	
	@Inject
	private ModelEJB modelEJB;
	
	@Inject
	private UserEJB userEJB;
	
	private ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	
	private String model;
	private List<Model> modelList;
	private String ubication;
	private int inventoryNumber;
	
	@PostConstruct
    public void init() {
        modelList= modelEJB.getAllPrinterModels();
    }
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public List<Model> getModelList() {
		return modelList;
	}
	
	public void setModelList(List<Model> modelList) {
		this.modelList = modelList;
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
		printerEJB.addPrinter(new Printer(getInventoryNumber(),getUbication()), modelEJB.findModel(getModel()));
		userEJB.assignPrinterToProfessor(currentUser.getName(), printerEJB.findPrinter(getInventoryNumber()));
		context.redirect("printerList.xhtml");
	}
}
