package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Subject;
import es.uvigo.esei.infraestructura.facade.SubjectGatewayBean;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;

@SessionScoped
@ManagedBean(name = "proffessorSubjects")
public class ProfessorSubjectsController {

	@Inject
	private Principal currentUser;
	
	@Inject
	private SubjectGatewayBean subjectGateway;
	
	@Inject
	private UserGatewayBean userGateway;

	private ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	
	public List<Subject> getProfessorSubjects(){
		this.userGateway.find(currentUser.getName());
		return this.userGateway.getCurrent().getSubjects();
	}
	
	public String getCurrentUserName(){
		return currentUser.getName();
	}
	
	public void removeSubjectFromProfessor(String subject) throws IOException{
		this.userGateway.getCurrent().getSubjects().remove(this.subjectGateway.find(subject));
		this.userGateway.update();
		this.userGateway.save();
	}
	
	public boolean isPetitionDone(String code){
		this.subjectGateway.find(code);
		return this.subjectGateway.getCurrent().getPetitionState() > 0;
	}
}
