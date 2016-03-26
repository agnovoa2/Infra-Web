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
@ManagedBean(name = "proffessorSubjects")
public class ProfessorSubjectsController {

	@Inject
	private Principal currentUser;
	
	@Inject
	private SubjectEJB subjectEJB;
	
	@Inject
	private UserEJB userEJB;

	private ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	
	public List<Subject> getProfessorSubjects(){
		return subjectEJB.getProfessorSubjects(currentUser.getName());
	}
	
	public List<Subject> getSubjects(){
		return subjectEJB.getSubjects();
	}
	
	public String getCurrentUserName(){
		return currentUser.getName();
	}
	
	public void removeSubjectFromProfessor(String subject) throws IOException{
		userEJB.removeSubjectFromProfessor(currentUser.getName(),subject);
		context.redirect("professorSubjects.xhtml");
	}
	
	public boolean isPetitionDone(String code){
		return subjectEJB.isPetitionDone(code);
	}
}
