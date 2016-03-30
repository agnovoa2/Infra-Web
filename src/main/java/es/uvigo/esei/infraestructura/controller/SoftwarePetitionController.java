package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Software;
import es.uvigo.esei.infraestructura.facade.SoftwareGatewayBean;
import es.uvigo.esei.infraestructura.facade.SubjectGatewayBean;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;

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

	private String software;
	private int softwareType;
	private String dowloadURL;
	private String code;
	private String description;
	
	@PostConstruct
	void init(){
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
	
	public void doAddSoftware(){
		this.softwareGateway.create(new Software(this.getSoftware(),this.getSoftwareType(),this.getDowloadURL()));
		this.softwareGateway.save();
	}
	
	public List<Software> getAllSoftware() throws IOException{
		return this.softwareGateway.getAll();
	}
	
	public boolean isSubjectSoftware(String softwareName){
		return this.subjectGateway.getCurrent().getSoftwares().contains(this.softwareGateway.find(softwareName));
	}
	
	public void doAddSoftwareToSubject(String softwareName){
		this.subjectGateway.getCurrent().getSoftwares().add(this.softwareGateway.find(softwareName));
		this.subjectGateway.save();
	}
	
	public void doRemoveSoftwareFromSubject(String softwareName){
		this.subjectGateway.getCurrent().getSoftwares().remove(this.softwareGateway.find(softwareName));
		this.subjectGateway.save();
	}
	
	public void doPetition() throws IOException{
		this.subjectGateway.getCurrent().setPetitionState(1);
		this.subjectGateway.getCurrent().setDescription(getDescription());
		this.subjectGateway.save();
		FacesContext.getCurrentInstance().getExternalContext().redirect("professorSubjects.xhtml");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
