package es.uvigo.esei.infraestructura.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uvigo.esei.infraestructura.entities.Petition;

@Stateless
public class PetitionEJB {
	@PersistenceContext
	EntityManager em;

	@EJB
	private UserAuthorizationEJB auth;

	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<Petition> findAllPetitions() {
		return em.createNamedQuery("findAllPetitions", Petition.class).getResultList();
	}

	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public Petition findPetition(int petitionNumber) {
		return em.find(Petition.class, petitionNumber);
	}

	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public void addPetition(Petition petition) {
		em.persist(petition);
	}
	
	
}
