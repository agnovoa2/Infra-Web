package es.uvigo.esei.infraestructura.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.uvigo.esei.infraestructura.entities.Subject;

@Stateless
public class SubjectEJB {
	
	@PersistenceContext
    private EntityManager em;

    @EJB
    private UserAuthorizationEJB auth;
    
    @RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<Subject> getProfessorSubjects(String login){
		Query query = em.createQuery("SELECT s FROM Subject s JOIN s.users u WHERE u.login = :login");
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
		return em.createNamedQuery("findAll").getResultList();
	}
    
    @RolesAllowed({ "INTERN", "PROFESSOR" })
    public boolean isProfessorSubject(String code, String login){
    	List<Subject> subjects = getProfessorSubjects(login);
    	Query query = em.createQuery("SELECT s FROM Subject s WHERE s.code = :code");
    	query.setParameter("code", code);
    	return subjects.contains(query.getSingleResult());
    }
}
