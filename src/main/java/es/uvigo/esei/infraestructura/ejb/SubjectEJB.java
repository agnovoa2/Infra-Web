package es.uvigo.esei.infraestructura.ejb;

import java.io.IOException;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.uvigo.esei.infraestructura.entities.Software;
import es.uvigo.esei.infraestructura.entities.Subject;

@Stateless
public class SubjectEJB {
	
	@PersistenceContext
    private EntityManager em;

    @EJB
    private UserAuthorizationEJB auth;
    
    @RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<Subject> getProfessorSubjects(String login){
		Query query = em.createQuery("SELECT s FROM Subject s JOIN s.users u WHERE u.login = :login", Subject.class);
		query.setParameter("login", login);
		return query.getResultList();
	}
    
    @RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<Subject> getRemainingSubjects(String login){
		List<Subject> toRet = getSubjects();
		List<Subject> profSub = getProfessorSubjects(login);
		toRet.removeAll(profSub);
		return toRet;
	}
    
    @RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<Subject> getSubjects(){
		return em.createNamedQuery("findAllSubjects",Subject.class).getResultList();
	}
    
    @RolesAllowed({ "INTERN", "PROFESSOR" })
    public boolean isProfessorSubject(String code, String login) throws IOException{
    	List<Subject> subjects = getProfessorSubjects(login);
    	for (Subject subject : subjects) {
			if(subject.getCode().equals(code))
				return true;
		}
    	return false;
    }
    
    @RolesAllowed({"INTERN","PROFESSOR"})
	public Subject findSubject(String subjectName){
		return em.find(Subject.class, subjectName);
	}
    
    @RolesAllowed({"INTERN","PROFESSOR"})
	public Subject findSubjectFromCode(String code){
    	Query query = em.createQuery("SELECT s FROM Subject s WHERE s.code =:code",Subject.class);
    	query.setParameter("code", code);
    	List<Subject> subjects = query.getResultList();
    	if(subjects.isEmpty())
    		return null;
    	return subjects.get(0);
    }
    
    @RolesAllowed({"INTERN","PROFESSOR"})
	public void addSoftwareToSubject(Software software, String code){
    	Subject subject = this.findSubjectFromCode(code);
    	subject.getSoftwares().add(software);
    	em.merge(subject);
    }
    
    @RolesAllowed({"INTERN","PROFESSOR"})
	public void removeSoftwareFromSubject(Software software, String code){
    	Subject subject = this.findSubjectFromCode(code);
    	subject.getSoftwares().remove(software);
    	em.merge(subject);
    }
    
    @RolesAllowed({"INTERN","PROFESSOR"})
	public void doPetition(String code, String description){
    	Subject subject = this.findSubjectFromCode(code);
    	subject.setDescription(description);
    	subject.setPetitionState(1);
    	em.merge(subject);
    }
    
    @RolesAllowed({"INTERN","PROFESSOR"})
   	public void solvePetition(String code){
       	Subject subject = this.findSubjectFromCode(code);
       	subject.setPetitionState(2);
       	em.merge(subject);
    }
    
    @RolesAllowed({"INTERN","PROFESSOR"})
    public boolean isPetitionDone(String code){
    	Subject subject = this.findSubjectFromCode(code);
       	return subject.getPetitionState() > 0;
    }
}
