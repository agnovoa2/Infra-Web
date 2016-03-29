package es.uvigo.esei.infraestructura.controller;

import java.security.Principal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.ejb.PrinterEJB;
import es.uvigo.esei.infraestructura.ejb.UserEJB;
import es.uvigo.esei.infraestructura.entities.Printer;

@ViewScoped
@ManagedBean(name = "printerList")
public class PrinterListController {
	
	@Inject
	private Principal currentUser;
	
	@Inject
	private PrinterEJB printerEJB;
	
	@Inject
	private UserEJB userEJB;
	
	private int printer;
	private List<Printer> notProfessorPrinters;
	
	@PostConstruct
    public void init() {
		updateSelectOnMenus();
    }
	
	public void updateSelectOnMenus(){
		notProfessorPrinters = printerEJB.getAllNonProfessorPrinters(userEJB.findUserByLogin(currentUser.getName()));
	}
	
	public void doRemovePrinter(int inventoryNumber){
		userEJB.removePrinterFromProfessor(currentUser.getName(), printerEJB.findPrinter(inventoryNumber));
		updateSelectOnMenus();
	}
	
	public void doAddExistingPrinter(){
		userEJB.assignPrinterToProfessor(currentUser.getName(), printerEJB.findPrinter(getPrinter()));
		updateSelectOnMenus();
	}

	public int getPrinter() {
		return printer;
	}

	public void setPrinter(int printer) {
		this.printer = printer;
	}

	public List<Printer> getNotProfessorPrinters() {
		return notProfessorPrinters;
	}

	public void setNotProfessorPrinters(List<Printer> notProfessorPrinters) {
		this.notProfessorPrinters = notProfessorPrinters;
	}
	
	public List<Printer> getAllPrinters(){
		return printerEJB.getAllProfessorPrinters(userEJB.findUserByLogin(currentUser.getName()));
	}
}
