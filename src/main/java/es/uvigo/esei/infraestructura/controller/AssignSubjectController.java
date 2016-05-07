package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Subject;
import es.uvigo.esei.infraestructura.facade.SubjectGatewayBean;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;

@ViewScoped
@ManagedBean(name = "assignSubject")
public class AssignSubjectController {
	
	@Inject
	private Principal currentUser;
	
	@Inject
	private UserGatewayBean userGateway;
	
	@Inject
	private SubjectGatewayBean subjectGateway;

	@PostConstruct
	void init(){
		this.userGateway.find(currentUser.getName());
	}
	
	public void assignSubjectToProfessor(String subject) throws IOException{
		this.userGateway.getCurrent().getSubjects().add(subjectGateway.find(subject));
		this.userGateway.save();
	}
	
	public List<Subject> getRemainingSubjects(){
		List<Subject> subjects = this.subjectGateway.getAll();	
		subjects.removeAll(this.userGateway.getCurrent().getSubjects());
		return subjects;
	}
}
