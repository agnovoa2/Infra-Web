package es.uvigo.esei.infraestructura.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.uvigo.esei.infraestructura.entities.Petition;

@Stateless
public class PetitionEJB {
	@PersistenceContext
	EntityManager em;
	
	@EJB
	private UserAuthorizationEJB auth;
	
	@RolesAllowed({"INTERN","PROFESSOR"})
	public List<Petition> findAllPetitions(){
		return em.createNamedQuery("findAllPetitions", Petition.class).getResultList();
	}
	
	@RolesAllowed({"INTERN","PROFESSOR"})
	public List<Petition> findPetition(int petitionNumber){
		Query query = em.createQuery("Select p From Petition p Where p.petitionNumber = :petitionNumber");
		query.setParameter("petitionNumber", petitionNumber);
		return query.getResultList();
	}
	
	@RolesAllowed({"INTERN","PROFESSOR"})
	public void addPetition(List<Petition> printerPetition){
		for (Petition petition : printerPetition) {
			em.persist(petition);
		}
	}
}
