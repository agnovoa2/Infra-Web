package es.uvigo.esei.infraestructura.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uvigo.esei.infraestructura.entities.PetitionRow;
import es.uvigo.esei.infraestructura.entities.PetitionRowId;

@Stateless
public class PetitionRowEJB {
	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private UserAuthorizationEJB auth;
	
	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<PetitionRow> findAllPetitionRows() {
		return em.createNamedQuery("findAllPetitionRows", PetitionRow.class).getResultList();
	}

	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public PetitionRow findPetitionRow(int petitionNumber, int inventoryNumber, String consumibleName) {
		PetitionRowId pk = new PetitionRowId();
		pk.setPetition(petitionNumber);
		pk.setConsumable(consumibleName);
		pk.setPrinter(inventoryNumber);
		return em.find(PetitionRow.class, pk);
	}

	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public void addPetitionRow(PetitionRow petition) {
		em.persist(petition);
	}
}
