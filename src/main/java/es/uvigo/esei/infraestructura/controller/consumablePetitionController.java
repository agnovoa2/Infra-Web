package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
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

import com.itextpdf.text.DocumentException;

import es.uvigo.esei.infraestructura.entities.Consumable;
import es.uvigo.esei.infraestructura.entities.Petition;
import es.uvigo.esei.infraestructura.entities.PetitionRow;
import es.uvigo.esei.infraestructura.facade.ConsumableGatewayBean;
import es.uvigo.esei.infraestructura.facade.PetitionGatewayBean;
import es.uvigo.esei.infraestructura.facade.PetitionRowGatewayBean;
import es.uvigo.esei.infraestructura.facade.PrinterGatewayBean;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;
import es.uvigo.esei.infraestructura.util.Mail;
import es.uvigo.esei.infraestructura.util.Report;

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

	@Inject
	private Mail mail;
	
	@Inject
	private Report report;
	
	private int invnum;
	private List<Consumable> printerConsumables;
	private List<String> quantities;
	private String textMessage;

	@PostConstruct
	public void init(){
		this.userGateway.find(currentUser.getName());
	}
	
	public void initLists(){
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

		Petition petition = new Petition(this.petitionGateway.nextPetitionNumber(),this.printerGateway.getCurrent(),sqlDate, this.userGateway.getCurrent());
		List<PetitionRow> petitionRows= new LinkedList<PetitionRow>();

		for (int i = 0; i < this.printerConsumables.size(); i++) {
			if(Integer.parseInt(quantities.get(i)) > 0){
				PetitionRow pr = new PetitionRow(petition, 
												 this.consumableGateway.find(printerConsumables.get(i).getConsumableName()),  
												 Integer.parseInt(quantities.get(i)));
				petitionRows.add(pr);
			}
		}
		petition.setPetitionRows(petitionRows);
		this.setTextMessage(petition);
		this.petitionGateway.create(petition);
		this.petitionGateway.save();
		
		for (PetitionRow petitionRow : petitionRows) {
			this.petitionRowGateway.create(petitionRow);
			this.petitionRowGateway.save();
		}
		
		
		mail.sendMail(this.getTextMessage(), "[Infraestructura] Nueva petición de consumibles");
		try {
			this.setTextMessage(petition);
			this.petitionGateway.create(petition);
			this.petitionGateway.save();
			
			for (PetitionRow petitionRow : petitionRows) {
				this.petitionRowGateway.create(petitionRow);
				this.petitionRowGateway.save();
			}
			
			
			mail.sendMail(this.getTextMessage(), "[Infraestructura] Nueva petición de consumibles");
			report.doSolicitudePDF(petition);
			
			
		} catch (DocumentException | IOException e) {
			// TODO Auto-generated catch block
			// mandar mensaje de que pasó algo al crear el pdf
		}
	}

	public List<String> getQuantities() {
		return quantities;
	}

	public void setQuantities(List<String> quantities) {
		this.quantities = quantities;
	}
	

	public void setTextMessage(Petition petition) {
		this.textMessage = ("Este es un mensaje autogenerado de la aplicación [Futuro nombre aqui]\n" 
				+ "\n"
				+ "El profesor " + userGateway.getCurrent().getName() + " "
				+ userGateway.getCurrent().getFirstSurname() + " "
				+ userGateway.getCurrent().getSecondSurname() + " ha realizado a fecha de " + petition.getPetitionDate()
				+ " la siguiente petición de consumibles para la impresora de la marca " + this.printerGateway.getCurrent().getModel().getTradeMark() 
				+ " modelo "+ this.printerGateway.getCurrent().getModel().getModelName()+ ".\n"
				+ " \n");
		for (PetitionRow petitionRow : petition.getPetitionRows()) {
			if(petitionRow.getConsumable().getColour() != null)
				this.textMessage += petitionRow.getConsumable().getColour() + ": " + petitionRow.getConsumable().getConsumableName() + " Cantidad: " + petitionRow.getQuantity() + "\n";
			else
				this.textMessage += petitionRow.getConsumable().getConsumableType().toString() + ": " + petitionRow.getConsumable().getConsumableName() + " Cantidad: " + petitionRow.getQuantity() +"\n";
			
		}
	}
	
	public String getTextMessage(){
		return this.textMessage;
	}
	
}
