package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.security.Principal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.ejb.SoftwareEJB;
import es.uvigo.esei.infraestructura.ejb.SubjectEJB;
import es.uvigo.esei.infraestructura.entities.Software;

@RequestScoped
@ManagedBean(name = "softwarePetition")
public class SoftwarePetitionController {

	@Inject
	private Principal currentUser;
	
	@Inject
	private SoftwareEJB softwareEJB;
	
	@Inject
	private SubjectEJB subjectEJB;

	private ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	private String software;
	private int softwareType;
	private String dowloadURL;
	private String description;
	private String code;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isProfessorSubject(String code) {
	    return subjectEJB.isProfessorSubject(code, currentUser.getName());
	}
	
	public void redirectIfNotProfessorSubject(String code) throws IOException {
		if (!this.isProfessorSubject(code))
            redirectToProfessorSubjects();
	}
	
	private void redirectToProfessorSubjects() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("professorSubjects.xhtml");
	}
	
	public void doAddSoftware() throws IOException{
		softwareEJB.addSoftware(new Software(this.getSoftware(),this.getSoftwareType(),this.getDowloadURL(),this.getDescription()));
		context.redirect("softwarePetition.xhtml?code=" + this.getCode());
	}
	
}
