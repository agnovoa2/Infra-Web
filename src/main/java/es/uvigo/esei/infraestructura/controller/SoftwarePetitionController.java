package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.ejb.SoftwareEJB;
import es.uvigo.esei.infraestructura.ejb.SubjectEJB;
import es.uvigo.esei.infraestructura.entities.Software;

@ViewScoped
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
	private String code;
	private String description;
	
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
	    return subjectEJB.isProfessorSubject(code, currentUser.getName());
	}
	
	public void redirectIfNotProfessorSubject() throws IOException {
		if (!this.isProfessorSubject(this.getCode()))
            redirectToProfessorSubjects();
	}
	
	private void redirectToProfessorSubjects() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("professorSubjects.xhtml");
	}
	
	public void doAddSoftware() throws IOException{
		softwareEJB.addSoftware(new Software(this.getSoftware(),this.getSoftwareType(),this.getDowloadURL()));
	}
	
	public List<Software> getAllSoftware() throws IOException{
		return softwareEJB.getAllSoftware();
	}
	
	public boolean isSubjectSoftware(String softwareName) throws IOException{
		return softwareEJB.isSubjectSoftware(softwareEJB.findSoftware(softwareName),subjectEJB.findSubjectFromCode(getCode()));
	}
	
	public void doAddSoftwareToSubject(String softwareName){
		subjectEJB.addSoftwareToSubject(softwareEJB.findSoftware(softwareName), getCode());
	}
	
	public void doRemoveSoftwareFromSubject(String softwareName) throws IOException{
		subjectEJB.removeSoftwareFromSubject(softwareEJB.findSoftware(softwareName), getCode());
	}
	
	public void doPetition() throws IOException{
		subjectEJB.doPetition(getCode(), getDescription());
		FacesContext.getCurrentInstance().getExternalContext().redirect("professorSubjects.xhtml");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
