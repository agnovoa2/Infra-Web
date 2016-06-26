package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Software;
import es.uvigo.esei.infraestructura.facade.SoftwareGatewayBean;
import es.uvigo.esei.infraestructura.facade.SubjectGatewayBean;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;
import es.uvigo.esei.infraestructura.util.Email;

@ViewScoped
@ManagedBean(name = "softwarePetition")
public class SoftwarePetitionController {

	@Inject
	private Principal currentUser;

	@Inject
	private UserGatewayBean userGateway;

	@Inject
	private SoftwareGatewayBean softwareGateway;

	@Inject
	private SubjectGatewayBean subjectGateway;

	@Inject
	private Email mail;

	private ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

	private String software;
	private int softwareType;
	private String dowloadURL;
	private String code;
	private String description;
	private String textMessage;

	@PostConstruct
	void init() {
		userGateway.find(currentUser.getName());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		if (subjectGateway.findByCode(code) != null) {
			description = subjectGateway.findByCode(code).getDescription();
		}
	}

	public String getSoftware() {
		return software;
	}

	public void setSoftware(String software) {
		this.software = software;
	}

	public int getSoftwareType() {
		return softwareType;
	}

	public void setSoftwareType(int softwareType) {
		this.softwareType = softwareType;
	}

	public String getDowloadURL() {
		return dowloadURL;
	}

	public void setDowloadURL(String dowloadURL) {
		this.dowloadURL = dowloadURL;
	}

	public boolean isProfessorSubject(String code) throws IOException {

		return userGateway.getCurrent().getSubjects().contains(subjectGateway.findByCode(code));
	}

	public void redirectIfNotProfessorSubject() throws IOException {
		if (!isProfessorSubject(getCode()))
			redirectToProfessorSubjects();
	}

	private void redirectToProfessorSubjects() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("professorSubjects.xhtml");
	}

	public void doAddSoftware() throws IOException {
		try {
			softwareGateway.create(new Software(getSoftware(), getSoftwareType(), getDowloadURL()));
			softwareGateway.save();
		} catch (SQLException e) {
			context.redirect("softwarePetition.xhtml?code=" + code + "&error=true");
		}
	}

	public List<Software> getAllSoftware() throws IOException {
		return softwareGateway.getAll();
	}

	public List<Software> getAllPrograms() throws IOException {
		return softwareGateway.getAllProgram();
	}

	public List<Software> getAllOperativeSystems() throws IOException {
		return softwareGateway.getAllOperativeSystem();
	}

	public boolean isSubjectSoftware(String softwareName) {
		return subjectGateway.getCurrent().getSoftwares().contains(softwareGateway.find(softwareName));
	}

	public void doAddSoftwareToSubject(String softwareName) {
		subjectGateway.getCurrent().getSoftwares().add(softwareGateway.find(softwareName));
		subjectGateway.save();
	}

	public void doRemoveSoftwareFromSubject(String softwareName) {
		subjectGateway.getCurrent().getSoftwares().remove(softwareGateway.find(softwareName));
		subjectGateway.save();
	}

	public void doSoftwarePetition() throws IOException {
		if (!subjectGateway.getCurrent().getSoftwares().isEmpty()) {
			subjectGateway.getCurrent().setPetitionState(1);
			subjectGateway.getCurrent().setDescription(getDescription());
			subjectGateway.save();
			setTextMessage();
			mail.sendEmail(getTextMessage(), "[Infraestructura] Nueva petición de software");
			FacesContext.getCurrentInstance().getExternalContext().redirect("professorSubjects.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("softwarePetition.xhtml?code=" + code + "&error=true");
		}
	}

	public String getDescription() {
		return description;
	}

	public String getSubject() {
		return subjectGateway.findByCode(code).getSubjectName();
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTextMessage() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		dateFormat.format(date);

		textMessage = ("Este es un mensaje autogenerado de la aplicación [Futuro nombre aqui]\n" + "\n" + "El profesor "
				+ userGateway.getCurrent().getName() + " " + userGateway.getCurrent().getFirstSurname() + " "
				+ userGateway.getCurrent().getSecondSurname() + " ha realizado a fecha de "
				+ new java.sql.Date(date.getTime()) + " la siguiente petición de software para la asignatura "
				+ subjectGateway.getCurrent().getSubjectName() + " \n");
		for (Software software : subjectGateway.getCurrent().getSoftwares()) {
			textMessage += (software.getSoftwareName() + "\n");
		}
		if (subjectGateway.getCurrent().getDescription() != null
				&& !subjectGateway.getCurrent().getDescription().equals(""))
			textMessage += ("\nDescripción opcional: " + subjectGateway.getCurrent().getDescription());
	}

	public String getTextMessage() {
		return textMessage;
	}

}
