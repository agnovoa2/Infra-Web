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
import es.uvigo.esei.infraestructura.util.Mail;

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
	private Mail mail;

	private ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

	private String software;
	private int softwareType;
	private String dowloadURL;
	private String code;
	private String description;
	private String textMessage;

	@PostConstruct
	void init() {
		this.userGateway.find(currentUser.getName());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

		return this.userGateway.getCurrent().getSubjects().contains(this.subjectGateway.findByCode(code));
	}

	public void redirectIfNotProfessorSubject() throws IOException {
		if (!this.isProfessorSubject(this.getCode()))
			redirectToProfessorSubjects();
	}

	private void redirectToProfessorSubjects() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("professorSubjects.xhtml");
	}

	public void doAddSoftware() throws IOException {
		try {
			this.softwareGateway.create(new Software(this.getSoftware(), this.getSoftwareType(), this.getDowloadURL()));
			this.softwareGateway.save();
		} catch (SQLException e) {
			context.redirect("softwarePetition.xhtml?code=" + code + "&error=true");
		}
	}

	public List<Software> getAllSoftware() throws IOException {
		return this.softwareGateway.getAll();
	}

	public boolean isSubjectSoftware(String softwareName) {
		return this.subjectGateway.getCurrent().getSoftwares().contains(this.softwareGateway.find(softwareName));
	}

	public void doAddSoftwareToSubject(String softwareName) {
		this.subjectGateway.getCurrent().getSoftwares().add(this.softwareGateway.find(softwareName));
		this.subjectGateway.save();
	}

	public void doRemoveSoftwareFromSubject(String softwareName) {
		this.subjectGateway.getCurrent().getSoftwares().remove(this.softwareGateway.find(softwareName));
		this.subjectGateway.save();
	}

	public void doPetition() throws IOException {
		if (!subjectGateway.getCurrent().getSoftwares().isEmpty()) {
			this.subjectGateway.getCurrent().setPetitionState(1);
			this.subjectGateway.getCurrent().setDescription(getDescription());
			this.subjectGateway.save();
			this.setTextMessage();
			this.mail.sendMail(this.getTextMessage(), "[Infraestructura] Nueva petición de software");
			FacesContext.getCurrentInstance().getExternalContext().redirect("professorSubjects.xhtml");
		}
		else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("softwarePetition.xhtml?code=" + code + "&error=true");
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTextMessage() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		dateFormat.format(date);

		this.textMessage = ("Este es un mensaje autogenerado de la aplicación [Futuro nombre aqui]\n" + "\n"
				+ "El profesor " + userGateway.getCurrent().getName() + " " + userGateway.getCurrent().getFirstSurname()
				+ " " + userGateway.getCurrent().getSecondSurname() + " ha realizado a fecha de "
				+ new java.sql.Date(date.getTime()) + " la siguiente petición de software para la asignatura "
				+ this.subjectGateway.getCurrent().getSubjectName() + " \n");
		for (Software software : this.subjectGateway.getCurrent().getSoftwares()) {
			this.textMessage += (software.getSoftwareName() + "\n");
		}
		if (this.subjectGateway.getCurrent().getDescription() != null
				&& !this.subjectGateway.getCurrent().getDescription().equals(""))
			this.textMessage += ("\nDescripción opcional: " + this.subjectGateway.getCurrent().getDescription());
	}

	public String getTextMessage() {
		return this.textMessage;
	}

}
