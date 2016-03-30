package es.uvigo.esei.infraestructura.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
		em.flush();
	}
	
	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public void updatePetition(Petition petition) {
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
