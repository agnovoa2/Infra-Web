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
		userGateway.find(currentUser.getName());
		return userGateway.getCurrent().getSubjects();
	}
	
	public String getCurrentUserName(){
		return currentUser.getName();
	}
	
	public void removeSubjectFromProfessor(String code) throws IOException{
		userGateway.getCurrent().getSubjects().remove(subjectGateway.findByCode(code));
		userGateway.save();
	}
	
	public boolean isPetitionDone(String code){
		subjectGateway.findByCode(code);
		return subjectGateway.getCurrent().getPetitionState() > 0;
	}
	
	public boolean isPetitionAccepted(String code){
		subjectGateway.findByCode(code);
		return subjectGateway.getCurrent().getPetitionState() > 1;
	}
	
	public void assignSubjectToProfessor(String subject) throws IOException{
		userGateway.getCurrent().getSubjects().add(subjectGateway.find(subject));
		userGateway.save();
	}
	
	public List<Subject> getRemainingSubjects(){
		List<Subject> subjects = subjectGateway.getAll();	
		subjects.removeAll(userGateway.getCurrent().getSubjects());
		return subjects;
	}
}
