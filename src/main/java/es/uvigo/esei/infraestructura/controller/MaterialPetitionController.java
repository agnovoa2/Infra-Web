package es.uvigo.esei.infraestructura.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Material;
import es.uvigo.esei.infraestructura.entities.MaterialPetition;
import es.uvigo.esei.infraestructura.entities.MaterialPetitionRow;
import es.uvigo.esei.infraestructura.facade.MaterialGatewayBean;
import es.uvigo.esei.infraestructura.facade.MaterialPetitionGatewayBean;
import es.uvigo.esei.infraestructura.facade.MaterialPetitionRowGatewayBean;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;
import es.uvigo.esei.infraestructura.util.Mail;

@ViewScoped
@ManagedBean(name = "materialPetition")
public class MaterialPetitionController {

	@Inject
	private Principal currentUser;

	@Inject
	private UserGatewayBean userGateway;

	@Inject
	private MaterialGatewayBean materialGateway;

	@Inject
	private MaterialPetitionGatewayBean materialPetitionGateway;

	@Inject
	private MaterialPetitionRowGatewayBean materialPetitionRowGateway;

	@Inject
	private Mail mail;

	private int id;
	private int quantity;
	private MaterialPetition petition;
	private List<MaterialPetitionRow> petitionRows = new ArrayList<MaterialPetitionRow>();
	private String textMessage;

	@PostConstruct
	public void init() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		dateFormat.format(date);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		petition = new MaterialPetition(sqlDate, userGateway.find(currentUser.getName()));
	}

	public void doAddMaterial() {
		MaterialPetitionRow pr = new MaterialPetitionRow(quantity, petition, materialGateway.find(id));
		if (!petitionRows.contains(pr)) {
			petitionRows.add(pr);
		}
	}

	public void doRemoveMaterial(MaterialPetitionRow id) {
		petitionRows.remove(id);
	}

	public void doChangeQuantity(MaterialPetitionRow id, int quantity) {
		int temp = petitionRows.get(petitionRows.indexOf(id)).getQuantity();
		switch (quantity) {
			case -1:
				if (temp > 0){
					petitionRows.get(petitionRows.indexOf(id)).setQuantity(temp + quantity);
				}
				break;
	
			case 1:
				if(temp < petitionRows.get(petitionRows.indexOf(id)).getMaterial().getQuantity())
					petitionRows.get(petitionRows.indexOf(id)).setQuantity(temp + quantity);
				break;
	
			default:
				break;
		}
	}

	public void doMaterialPetition() {
		materialPetitionGateway.create(petition);
		materialPetitionGateway.save();
		for (MaterialPetitionRow materialPetitionRow : petitionRows) {
			materialPetitionRowGateway.create(materialPetitionRow);
			materialPetitionRowGateway.save();
		}
		setTextMessage();
		mail.sendMail(textMessage, "Nueva petición de material");
	}

	public void setTextMessage() {
		this.textMessage = ("Este es un mensaje autogenerado de la aplicación [Futuro nombre aqui]\n" + "\n"
				+ "El usuario " + userGateway.getCurrent().getName() + " " + userGateway.getCurrent().getFirstSurname()
				+ " " + userGateway.getCurrent().getSecondSurname() + " ha realizado a fecha de "
				+ petition.getPetitionDate() + " la siguiente petición de materiales.\n" + " \n");
		for (MaterialPetitionRow petitionRow : petitionRows) {
			this.textMessage += petitionRow.getMaterial().getMaterial().toString() + ": "
					+ petitionRow.getMaterial().getMaterialName() + " Cantidad: " + petitionRow.getQuantity() + "\n";
		}
	}

	public String getTextMessage() {
		return this.textMessage;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int monitorQuantity) {
		this.quantity = monitorQuantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<MaterialPetitionRow> getPetitionRows() {
		return petitionRows;
	}

	public void setPetitionRows(List<MaterialPetitionRow> petitionRows) {
		this.petitionRows = petitionRows;
	}

	public List<Material> getAllMaterial(String material) {
		switch (material) {
		case "monitor":
			return materialGateway.getAllLendableMonitors();
		case "ram":
			return materialGateway.getAllLendableRamMemories();
		case "disco duro":
			return materialGateway.getAllLendableHardDrives();
		case "otros":
			return materialGateway.getAllLendableOthers();
		default:
			return null;
		}
	}

}
