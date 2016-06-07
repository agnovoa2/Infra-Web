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
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.ConsumablePetition;
import es.uvigo.esei.infraestructura.entities.ConsumablePetitionRow;
import es.uvigo.esei.infraestructura.entities.Incidence;
import es.uvigo.esei.infraestructura.entities.MaterialPetition;
import es.uvigo.esei.infraestructura.entities.MaterialPetitionRow;
import es.uvigo.esei.infraestructura.entities.Software;
import es.uvigo.esei.infraestructura.entities.Subject;
import es.uvigo.esei.infraestructura.facade.ConsumablePetitionGatewayBean;
import es.uvigo.esei.infraestructura.facade.ConsumablePetitionRowGatewayBean;
import es.uvigo.esei.infraestructura.facade.IncidenceGatewayBean;
import es.uvigo.esei.infraestructura.facade.MaterialGatewayBean;
import es.uvigo.esei.infraestructura.facade.MaterialPetitionGatewayBean;
import es.uvigo.esei.infraestructura.facade.MaterialPetitionRowGatewayBean;
import es.uvigo.esei.infraestructura.facade.SubjectGatewayBean;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;
import es.uvigo.esei.infraestructura.util.Mail;

@ViewScoped
@ManagedBean(name = "index")
public class IndexController {

	@Inject
	private Principal currentUser;

	@Inject
	private SubjectGatewayBean subjectGateway;

	@Inject
	private IncidenceGatewayBean incidenceGateway;

	@Inject
	private ConsumablePetitionGatewayBean consumablePetitionGateway;

	@Inject
	private ConsumablePetitionRowGatewayBean consumablePetitionRowGateway;

	@Inject
	private MaterialPetitionGatewayBean materialPetitionGateway;

	@Inject
	private MaterialPetitionRowGatewayBean materialPetitionRowGateway;

	@Inject
	private MaterialGatewayBean materialGateway;

	@Inject
	private UserGatewayBean userGateway;

	@Inject
	private Mail mail;
	String textMessage;
	
	public void doRemoveConsumablePetition(int id) {
		consumablePetitionGateway.find(id);
		for (ConsumablePetitionRow petitionRow : consumablePetitionGateway.getCurrent().getPetitionRows()) {
			consumablePetitionRowGateway.remove(petitionRow.getId());
			consumablePetitionRowGateway.save();
		}
		consumablePetitionGateway.getCurrent().setPetitionRows(null);
		consumablePetitionGateway.remove(id);
		consumablePetitionGateway.save();
	}

	public void doRemovematerialPetition(int id) {
		materialPetitionGateway.find(id);
		for (MaterialPetitionRow petitionRow : materialPetitionGateway.getCurrent().getPetitionRows()) {
			materialGateway.find(petitionRow.getMaterial().getId());
			materialGateway.getCurrent()
					.setQuantity(materialGateway.getCurrent().getQuantity() + petitionRow.getQuantity());
			materialGateway.save();
			materialPetitionRowGateway.remove(petitionRow.getId());
			materialPetitionRowGateway.save();
		}
		materialPetitionGateway.getCurrent().setPetitionRows(null);
		materialPetitionGateway.remove(id);
		materialPetitionGateway.save();
	}

	public void doRemoveSoftwarePetition(String subjectName) throws IOException {
		subjectGateway.find(subjectName);
		subjectGateway.getCurrent().setSoftwares(null);
		subjectGateway.getCurrent().setPetitionState(0);
		subjectGateway.save();
		FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
	}

	public void doRepeatSoftwarePetition(String subjectName) throws IOException {
		subjectGateway.find(subjectName);
		subjectGateway.getCurrent().setPetitionState(1);
		subjectGateway.getCurrent().setDescription(subjectGateway.getCurrent().getDescription());
		subjectGateway.save();
		setTextMessage();
		mail.sendMail(textMessage, "[Infraestructura] Nueva petición de software");
		FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
	}

	public void setTextMessage() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		dateFormat.format(date);

		textMessage = ("Este es un mensaje autogenerado de la aplicación [Futuro nombre aqui]\n" + "\n"
				+ "El profesor " + userGateway.getCurrent().getName() + " " + userGateway.getCurrent().getFirstSurname()
				+ " " + userGateway.getCurrent().getSecondSurname() + " ha realizado a fecha de "
				+ new java.sql.Date(date.getTime()) + " la siguiente petición de software para la asignatura "
				+ subjectGateway.getCurrent().getSubjectName() + " \n");
		for (Software software : subjectGateway.getCurrent().getSoftwares()) {
			textMessage += (software.getSoftwareName() + "\n");
		}
		if (subjectGateway.getCurrent().getDescription() != null
				&& !subjectGateway.getCurrent().getDescription().equals(""))
			textMessage += ("\nDescripción opcional: " + subjectGateway.getCurrent().getDescription());
	}
	
	public List<Incidence> getAllIncidences() {
		return incidenceGateway.getAllUnsolvedIncidences();
	}

	public List<Subject> getAllUnsolvedSoftwarePetitions() {
		return subjectGateway.getAllUnsolvedPetitions();
	}

	public List<Subject> getAllSoftwarePetitions() {
		return subjectGateway.getAllPetitions();
	}

	public List<MaterialPetition> getAllMaterialPetitions() {
		return materialPetitionGateway.getAllPetitions();
	}

	public List<ConsumablePetition> getAllConsumablePetitions() {
		return consumablePetitionGateway.getAllPetitions();
	}

	public List<ConsumablePetition> getMyConsumablePetitions() {
		return consumablePetitionGateway.getAllUserConsumablePetitions(currentUser.getName());
	}

	public List<MaterialPetition> getMyMaterialPetitions() {
		return materialPetitionGateway.getAllUserMaterialPetitions(currentUser.getName());
	}

	public List<Subject> getMySoftwarePetitions() {
		return professorPetitions(userGateway.find(currentUser.getName()).getSubjects());
	}

	private List<Subject> professorPetitions(List<Subject> allSubjects) {
		List<Subject> toRet = new LinkedList<Subject>();
		for (Subject subject : allSubjects) {
			if (subject.getPetitionState() > 0)
				toRet.add(subject);
		}
		return toRet;
	}
}
