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
public class SoftwareEJB {
	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private UserAuthorizationEJB auth;
	
	@RolesAllowed({"INTERN","PROFESSOR"})
	public void addSoftware(Software software){
		em.persist(software);
	}
	
	@RolesAllowed({"INTERN","PROFESSOR"})
	public List<Software> getAllSoftware(){
		return em.createNamedQuery("findAllSoftware",Software.class).getResultList();
	}
	
	@RolesAllowed({"INTERN","PROFESSOR"})
	public Software findSoftware(String softwareName){
		return em.find(Software.class, softwareName);
	}
	
	@RolesAllowed({"INTERN","PROFESSOR"})
	public boolean isSubjectSoftware(Software software, Subject subject) throws IOException{
		Query query = em.createQuery("SELECT s FROM Software s JOIN s.subjects su WHERE su.subjectName = :subjectName",Software.class);
		query.setParameter("subjectName", subject.getSubjectName());
		List<Software> softwares = query.getResultList();
		for (Software soft : softwares) {
			if(soft.getSoftwareName().equals(software.getSoftwareName()))
				return true;
		}
		return false;
	}
}
