package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.ejb.SubjectEJB;
import es.uvigo.esei.infraestructura.ejb.UserEJB;
import es.uvigo.esei.infraestructura.entities.Subject;

@RequestScoped
@ManagedBean(name = "assignSubject")
public class AssignSubjectController {

	@Inject
	private Principal currentUser;
	
	@Inject
	private UserEJB userEJB;
	
	@Inject
	private SubjectEJB subjectEJB;

	private ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	
	public void assignSubjectToProfessor(String subject) throws IOException{
		userEJB.assignSubjectToProfessor(currentUser.getName(),subject);
		context.redirect("assignSubject.xhtml");
	}
	
	public List<Subject> getRemainingSubjects(){
		return subjectEJB.getRemainingSubjects(currentUser.getName());
	}
}
