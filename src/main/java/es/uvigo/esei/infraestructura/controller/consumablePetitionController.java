package es.uvigo.esei.infraestructura.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Consumable;
import es.uvigo.esei.infraestructura.entities.Petition;
import es.uvigo.esei.infraestructura.entities.PetitionRow;
import es.uvigo.esei.infraestructura.facade.ConsumableGatewayBean;
import es.uvigo.esei.infraestructura.facade.PetitionGatewayBean;
import es.uvigo.esei.infraestructura.facade.PetitionRowGatewayBean;
import es.uvigo.esei.infraestructura.facade.PrinterGatewayBean;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;

@ViewScoped
@ManagedBean(name = "consumablePetition")
public class ConsumablePetitionController {

	@Inject
	private Principal currentUser;

	@Inject
	private PrinterGatewayBean printerGateway;

	@Inject
	private UserGatewayBean userGateway;

	@Inject
	private ConsumableGatewayBean consumableGateway;
	
	@Inject
	private PetitionGatewayBean petitionGateway;
	
	@Inject
	private PetitionRowGatewayBean petitionRowGateway;

	private int invnum;
	private List<Consumable> printerConsumables;
	private List<String> quantities;

	@PostConstruct
	public void init(){
		this.userGateway.find(currentUser.getName());
	}
	
	public void initLists(){
		System.out.println("entré");
		this.printerConsumables = this.printerGateway.find(getInvnum()).getModel().getConsumables();
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
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		dateFormat.format(date);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		Petition petition = new Petition(this.petitionGateway.nextPetitionNumber(),sqlDate, this.userGateway.getCurrent());
		this.petitionGateway.create(petition);
		this.petitionGateway.save();
//		petitionEJB.addPetition(petition);
		for (int i = 0; i < this.printerConsumables.size(); i++) {
			if(Integer.parseInt(quantities.get(i)) > 0){
				PetitionRow pr = new PetitionRow(petition, 
												 this.consumableGateway.find(printerConsumables.get(i).getConsumableName()), 
												 this.printerGateway.find(getInvnum()), 
												 Integer.parseInt(quantities.get(i)));
				this.petitionRowGateway.create(pr);
				this.petitionRowGateway.save();
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
