package es.uvigo.esei.infraestructura.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.MaterialPetition;
import es.uvigo.esei.infraestructura.entities.MaterialPetitionRow;
import es.uvigo.esei.infraestructura.facade.MaterialGatewayBean;
import es.uvigo.esei.infraestructura.facade.MaterialPetitionGatewayBean;
import es.uvigo.esei.infraestructura.util.Mail;

@ViewScoped
@ManagedBean(name = "materialPetitionManagement")
public class MaterialPetitionManagementController {

	@Inject
	private MaterialPetitionGatewayBean materialPetitionGateway;
	
	@Inject
	private MaterialGatewayBean materialGateway;

	@Inject
	private Mail mail;

	private String textMessage;

	public void doConfirmPetition(int id) {
		materialPetitionGateway.find(id);
		materialPetitionGateway.getCurrent().setPetitionState(1);
		materialPetitionGateway.save();
		setTextMessage();
		mail.sendMail(textMessage, "Confirmación de petición de materiales.", materialPetitionGateway.getCurrent().getUser().getEmail());
	}

	public void doRetrievePetition(int id) {
		materialPetitionGateway.find(id);
		materialPetitionGateway.getCurrent().setPetitionState(2);
		materialPetitionGateway.save();
		for (MaterialPetitionRow materialPetitionRow : materialPetitionGateway.getCurrent().getPetitionRows()) {
			materialGateway.find(materialPetitionRow.getMaterial().getId());
			materialGateway.getCurrent().setQuantity(materialGateway.getCurrent().getQuantity() + materialPetitionRow.getQuantity());
			materialGateway.save();
		}
	}

	public List<MaterialPetition> getAllPetitions() {
		return materialPetitionGateway.getAllPetitions();
	}

	public List<MaterialPetition> getAllDonePetitions() {
		return materialPetitionGateway.getAllDonePetitions();
	}

	public List<MaterialPetition> getAllRetrievedPetitions() {
		return materialPetitionGateway.getAllRetirevedPetitions();
	}

	public String getTextMessage() {
		return textMessage;
	}

	public void setTextMessage() {
		this.textMessage = ("Este es un mensaje autogenerado de la aplicación [Futuro nombre aqui]\n" + "\n"
				+ "Su petición de material ha sido aprobada, por favor, pase por el departamento de infraestructura en horario de apertura.\n Un saludo.");
	}
}
