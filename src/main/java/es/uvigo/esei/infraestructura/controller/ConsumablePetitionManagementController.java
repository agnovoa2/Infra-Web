package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.itextpdf.text.DocumentException;

import es.uvigo.esei.infraestructura.entities.ConsumablePetition;
import es.uvigo.esei.infraestructura.facade.ConsumablePetitionGatewayBean;
import es.uvigo.esei.infraestructura.util.Mail;
import es.uvigo.esei.infraestructura.util.Report;

@ViewScoped
@ManagedBean(name = "consumablePetitionManagement")
public class ConsumablePetitionManagementController {

	@Inject
	Report report;

	@Inject
	Mail mail;
	
	@Inject
	ConsumablePetitionGatewayBean consumablePetitionGateway;

	private String textMessage;
	
	public void doConfirmPetition(int petitionNum) {
		try {
			consumablePetitionGateway.find(petitionNum);
			consumablePetitionGateway.getCurrent().setPetitionState(1);
			consumablePetitionGateway.save();
			report.doRetrievePDF(consumablePetitionGateway.getCurrent());
			setTextMessage();
			mail.sendMail(textMessage, "[Infraestructura] petición de consumibles aceptada", consumablePetitionGateway.getCurrent().getUser().getEmail());
		} catch (MalformedURLException e) {
		} catch (DocumentException e) {
		} catch (IOException e) {
		}
	}

	private void setTextMessage(){
		textMessage = ("Este es un mensaje autogenerado de la aplicación InfraWEB\n" 
				+ "\n"
				+ "Su petición de consumibles ha sido aceptada, por favor, póngase en contacto con infraestructura para realizar la entrega de consumibles "
				+ "\n"
				+ "Un saludo."); 
	}
	
	public List<ConsumablePetition> getAllPetitions() {
		return consumablePetitionGateway.getAllPetitions();
	}

	public List<ConsumablePetition> getAllDonePetitions() {
		return consumablePetitionGateway.getAllDonePetitions();
	}
}
