package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Subject;
import es.uvigo.esei.infraestructura.facade.SubjectGatewayBean;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;

@ViewScoped
@ManagedBean(name = "proffessorSubjects")
public class ProfessorSubjectsController {

	@Inject
	private Principal currentUser;
	
	@Inject
	private SubjectGatewayBean subjectGateway;
	
	@Inject
	private UserGatewayBean userGateway;
	
	public List<Subject> getProfessorSubjects(){
		this.userGateway.find(currentUser.getName());
		return this.userGateway.getCurrent().getSubjects();
	}
	
	public String getCurrentUserName(){
		return currentUser.getName();
	}
	
	public void removeSubjectFromProfessor(String code) throws IOException{
		this.userGateway.getCurrent().getSubjects().remove(this.subjectGateway.findByCode(code));
		this.userGateway.save();
	}
	
	public boolean isPetitionDone(String code){
		this.subjectGateway.findByCode(code);
		return this.subjectGateway.getCurrent().getPetitionState() > 0;
	}
}
