package es.uvigo.esei.infraestructura.controller;

import java.security.Principal;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.ejb.PrinterEJB;
import es.uvigo.esei.infraestructura.ejb.UserEJB;
import es.uvigo.esei.infraestructura.entities.Printer;

@RequestScoped
@ManagedBean(name = "printerList")
public class PrinterListController {
	
	@Inject
	private Principal currentUser;
	
	@Inject
	private PrinterEJB printerEJB;
	
	@Inject
	private UserEJB userEJB;
	
	public List<Printer> getAllPrinters(){
		return printerEJB.getAllProfessorPrinters(userEJB.findUserByLogin(currentUser.getName()));
	}
}
