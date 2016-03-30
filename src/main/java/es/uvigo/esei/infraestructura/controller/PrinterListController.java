package es.uvigo.esei.infraestructura.controller;

import java.security.Principal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Printer;
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
	
	private int printer;
	
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

	public List<Printer> getNotProfessorPrinters() {
		List<Printer> printers = this.printerGateway.getAll();
		printers.removeAll(this.userGateway.getCurrent().getPrinters());
		return printers;
	}
	
	public List<Printer> getAllProfessorPrinters(){
		return this.userGateway.getCurrent().getPrinters();
	}
}
