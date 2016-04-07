package es.uvigo.esei.infraestructura.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uvigo.esei.infraestructura.entities.ConsumablePetitionRow;

@Stateless
public class PetitionRowEJB {
	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private UserAuthorizationEJB auth;
	
	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<ConsumablePetitionRow> findAllPetitionRows() {
		return em.createNamedQuery("findAllPetitionRows", ConsumablePetitionRow.class).getResultList();
	}

	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public void addPetitionRow(ConsumablePetitionRow petition) {
		em.persist(petition);
	}
}
