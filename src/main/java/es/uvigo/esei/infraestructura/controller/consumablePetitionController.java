package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.ejb.ConsumableEJB;
import es.uvigo.esei.infraestructura.ejb.PetitionEJB;
import es.uvigo.esei.infraestructura.ejb.PetitionRowEJB;
import es.uvigo.esei.infraestructura.ejb.PrinterEJB;
import es.uvigo.esei.infraestructura.ejb.UserEJB;
import es.uvigo.esei.infraestructura.entities.Consumable;
import es.uvigo.esei.infraestructura.entities.Petition;
import es.uvigo.esei.infraestructura.entities.PetitionRow;

@ViewScoped
@ManagedBean(name = "consumablePetition")
public class ConsumablePetitionController {

	@Inject
	private Principal currentUser;

	@Inject
	private PrinterEJB printerEJB;

	@Inject
	private UserEJB userEJB;

	@Inject
	private ConsumableEJB consumableEJB;
	
	@Inject
	private PetitionEJB petitionEJB;
	
	@Inject
	private PetitionRowEJB petitionRowEJB;

	private int invnum;
	private List<Consumable> printerConsumables;
	private List<String> quantities;

	public void init() throws IOException {
		this.printerConsumables = printerEJB.findPrinter(getInvnum()).getModel().getConsumables();
		this.quantities = new LinkedList<String>();
		for(int i = 0; i < printerConsumables.size(); i++){
			quantities.add("0");
		}
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

	public void doAddPetition() {
		List<PetitionRow> petitionRows = new LinkedList<PetitionRow>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		dateFormat.format(date);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		Petition petition = new Petition(sqlDate, userEJB.findUserByLogin(currentUser.getName()));
		petitionEJB.addPetition(petition);
		for (int i = 0; i < this.printerConsumables.size(); i++) {
			if(Integer.parseInt(quantities.get(0)) > 0){
				PetitionRow pr = new PetitionRow(petition, 
												 consumableEJB.find(printerConsumables.get(i).getConsumableName()), 
												 printerEJB.findPrinter(getInvnum()), 
												 Integer.parseInt(quantities.get(i)));
				petitionRowEJB.addPetitionRow(pr);
			}
		}		
	}

	public List<String> getQuantities() {
		return quantities;
	}

	public void setQuantities(List<String> quantities) {
		this.quantities = quantities;
	}
}
