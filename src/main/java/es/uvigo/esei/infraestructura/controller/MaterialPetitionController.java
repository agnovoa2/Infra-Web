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
import es.uvigo.esei.infraestructura.exception.EmptyPetitionException;
import es.uvigo.esei.infraestructura.facade.MaterialGatewayBean;
import es.uvigo.esei.infraestructura.facade.MaterialPetitionGatewayBean;
import es.uvigo.esei.infraestructura.facade.MaterialPetitionRowGatewayBean;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;
import es.uvigo.esei.infraestructura.util.Email;

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
	private Email mail;

	private int id;
	private int quantity;
	private MaterialPetition petition;
	private List<MaterialPetitionRow> petitionRows = new ArrayList<MaterialPetitionRow>();
	private String textMessage;
	private String message;
	private boolean error = false;
	private boolean success = false;

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
			if (temp > 0) {
				petitionRows.get(petitionRows.indexOf(id)).setQuantity(temp + quantity);
			}
			break;

		case 1:
			if (temp < petitionRows.get(petitionRows.indexOf(id)).getMaterial().getQuantity())
				petitionRows.get(petitionRows.indexOf(id)).setQuantity(temp + quantity);
			break;

		default:
			break;
		}
	}

	public void doMaterialPetition() {
		boolean flag = false;
		materialPetitionGateway.create(petition);
		materialPetitionGateway.save();
		for (MaterialPetitionRow materialPetitionRow : petitionRows) {
			if (materialPetitionRow.getQuantity() > 0) {
				materialPetitionRowGateway.create(materialPetitionRow);
				materialGateway.find(materialPetitionRow.getMaterial().getId());
				materialGateway.getCurrent()
						.setQuantity(materialGateway.getCurrent().getQuantity() - materialPetitionRow.getQuantity());
				materialGateway.save();
				materialPetitionRowGateway.save();
				flag = true;
			}
		}
		try {
			if (!flag) {
				throw new EmptyPetitionException();
			} else {
				materialPetitionGateway.save();
				setTextMessage();
				mail.sendEmail(textMessage, "Nueva petición de material");
				error = false;
				success = true;
				message = "Petición realizada con éxito";
			}
		} catch (EmptyPetitionException e) {
			error = true;
			success = false;
			message = "No ha escogido ningún material para realizar su petición";
		}
	}

	public void setTextMessage() {
		textMessage = ("Este es un mensaje autogenerado de la aplicación InfraWEB\n" + "\n" + "El usuario "
				+ userGateway.getCurrent().getName() + " " + userGateway.getCurrent().getFirstSurname() + " "
				+ userGateway.getCurrent().getSecondSurname() + " ha realizado a fecha de " + petition.getPetitionDate()
				+ " la siguiente petición de materiales.\n" + " \n");
		for (MaterialPetitionRow materialPetitionRow : petitionRows) {
			if (materialPetitionRow.getQuantity() > 0) {
				textMessage += materialPetitionRow.getMaterial().getMaterial().toString() + ": "
						+ materialPetitionRow.getMaterial().getMaterialName() + " Cantidad: "
						+ materialPetitionRow.getQuantity() + "\n";
			}
		}
	}

	public String getTextMessage() {
		return textMessage;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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
