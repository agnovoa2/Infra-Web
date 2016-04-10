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
import es.uvigo.esei.infraestructura.util.Report;

@ViewScoped
@ManagedBean(name = "consumablePetitionManagement")
public class ConsumablePetitionManagerController {

	@Inject
	Report report;

	@Inject
	ConsumablePetitionGatewayBean petitionGateway;

	public void doConfirmPetition(int petitionNum) {
		try {
			petitionGateway.find(petitionNum);
			petitionGateway.getCurrent().setPetitionState(1);
			petitionGateway.save();
			report.doRetrievePDF(petitionGateway.getCurrent());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<ConsumablePetition> getAllPetitions() {
		return petitionGateway.getAllPetitions();
	}

	public List<ConsumablePetition> getAllDonePetitions() {
		return petitionGateway.getAllDonePetitions();
	}
}
