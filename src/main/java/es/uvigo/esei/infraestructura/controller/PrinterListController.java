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
	private String message;
	
	@PostConstruct
    public void init() {
		userGateway.find(currentUser.getName());
    }
		
	public void doRemovePrinter(int inventoryNumber){

		userGateway.getCurrent().getPrinters().remove(printerGateway.find(inventoryNumber));
		userGateway.save();
	}
	
	public void doAddExistingPrinter(){
		userGateway.getCurrent().getPrinters().add(printerGateway.find(getPrinter()));
		userGateway.save();
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
		return modelGateway.getAll();
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Printer> getNotProfessorPrinters() {
		List<Printer> printers = printerGateway.getAll();
		printers.removeAll(userGateway.getCurrent().getPrinters());
		return printers;
	}
	
	public List<Printer> getAllProfessorPrinters(){
		return userGateway.getCurrent().getPrinters();
	}
	
	public void doAddPrinter() throws IOException {
		try {
			printerGateway.create(new Printer(getPrinter(), getUbication()));
			printerGateway.getCurrent().setModel(modelGateway.find(getModel()));
			printerGateway.save();

			userGateway.getCurrent().getPrinters().add(printerGateway.getCurrent());
			userGateway.save();
			success = true;
			error = false;
		} catch (SQLException e) {
			message = e.getMessage();
			success = false;
			error = true;
		}
	}
}
