package es.uvigo.esei.infraestructura.controller;

import java.security.Principal;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.ejb.SubjectEJB;
import es.uvigo.esei.infraestructura.entities.Subject;

@ViewScoped
@ManagedBean(name = "listProffessorSubjects")
public class ListProfessorSubjectController {

	@Inject
	private Principal currentUser;
	
	@Inject
	private SubjectEJB subjectEJB;

	public List<Subject> getProfessorSubjects(){
		return subjectEJB.getProfessorSubjects(currentUser.getName());
	}
	
	public List<Subject> getSubjects(){
		return subjectEJB.getSubjects();
	}
	
	public List<Subject> getRemainingSubjects(){
		return subjectEJB.getRemainingSubjects(currentUser.getName());
	}
	
	public String getCurrentUserName(){
		return currentUser.getName();
	}
}
