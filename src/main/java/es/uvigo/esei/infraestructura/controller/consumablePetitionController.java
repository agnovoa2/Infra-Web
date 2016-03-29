package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.ejb.PrinterEJB;
import es.uvigo.esei.infraestructura.entities.Consumable;
import es.uvigo.esei.infraestructura.entities.Printer;

@RequestScoped
@ManagedBean(name="consumablePetition")
public class consumablePetitionController {
	
	@Inject
	private Principal currentUser;
	
	@Inject
	private PrinterEJB printerEJB;
	
	private int invnum;
	private List<Consumable> printerConsumables;
	
	public void init() throws IOException{
		this.printerConsumables = printerEJB.findPrinter(getInvnum()).getModel().getConsumables();
	}

	public List<Consumable> getPrinterConsumables() {
		return printerConsumables;
	}

	public void setPrinterConsumables(List<Consumable> printerConsumables) {
		this.printerConsumables = printerConsumables;
	}

	public int getInvnum() {
		return invnum;
	}

	public void setInvnum(int invnum) {
		this.invnum = invnum;
	}
}
