package es.uvigo.esei.infraestructura.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import es.uvigo.esei.infraestructura.entities.ConsumablePetition;

@Stateless
public class PetitionEJB {
	@PersistenceContext
	EntityManager em;

	@EJB
	private UserAuthorizationEJB auth;

	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<ConsumablePetition> findAllPetitions() {
		return em.createNamedQuery("findAllPetitions", ConsumablePetition.class).getResultList();
	}

	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public ConsumablePetition findPetition(int petitionNumber) {
		return em.find(ConsumablePetition.class, petitionNumber);
	}

	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public void addPetition(ConsumablePetition petition) {
		em.persist(petition);
		em.flush();
	}
	
	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public void updatePetition(ConsumablePetition petition) {
		em.merge(petition);
	}
	
	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public int nextPetitionNumber() {
		try{
		Integer i = em.createQuery("Select max(p.petitionNumber) From Petition p",Integer.class).getSingleResult();
		return i.intValue()+1;
		}
		catch(NullPointerException e){
			return 1;
		}
	}
}
