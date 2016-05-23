package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Model;
import es.uvigo.esei.infraestructura.entities.Printer;
import es.uvigo.esei.infraestructura.facade.ModelGatewayBean;
import es.uvigo.esei.infraestructura.facade.PrinterGatewayBean;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;

@ViewScoped
@ManagedBean(name = "printerList")
public class PrinterListController {
	
	@Inject
	private Principal currentUser;
	
	@Inject
	private PrinterGatewayBean printerGateway;
	
	@Inject
	private UserGatewayBean userGateway;
	
	@Inject
	private ModelGatewayBean modelGateway;
	
	private int printer;
	private String model;
	private String ubication;
	private boolean success = false;
	private boolean error = false;
	
	@PostConstruct
    public void init() {
		userGateway.find(currentUser.getName());
    }
		
	public void doRemovePrinter(int inventoryNumber){

		this.userGateway.getCurrent().getPrinters().remove(this.printerGateway.find(inventoryNumber));
		this.userGateway.save();
	}
	
	public void doAddExistingPrinter(){
		this.userGateway.getCurrent().getPrinters().add(this.printerGateway.find(getPrinter()));
		this.userGateway.save();
	}

	public int getPrinter() {
		return printer;
	}

	public void setPrinter(int printer) {
		this.printer = printer;
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
	
	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<Printer> getNotProfessorPrinters() {
		List<Printer> printers = this.printerGateway.getAll();
		printers.removeAll(this.userGateway.getCurrent().getPrinters());
		return printers;
	}
	
	public List<Printer> getAllProfessorPrinters(){
		return this.userGateway.getCurrent().getPrinters();
	}
	
	public void doAddPrinter() throws IOException {
		try {
			this.printerGateway.create(new Printer(getPrinter(), getUbication()));
			this.printerGateway.getCurrent().setModel(modelGateway.find(getModel()));
			this.printerGateway.save();

			this.userGateway.getCurrent().getPrinters().add(this.printerGateway.getCurrent());
			this.userGateway.save();
		} catch (SQLException e) {
		}
	}
}
