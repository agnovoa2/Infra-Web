package es.uvigo.esei.infraestructura.ejb;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uvigo.esei.infraestructura.entities.Software;

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
}
