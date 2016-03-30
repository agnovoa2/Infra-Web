package es.uvigo.esei.infraestructura.facade;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import es.uvigo.esei.infraestructura.ejb.UserAuthorizationEJB;
import es.uvigo.esei.infraestructura.entities.PetitionRow;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class PetitionRowGatewayBean {
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@EJB
	private UserAuthorizationEJB auth;

	private PetitionRow current;

	public PetitionRow find(String id) {
		this.current = this.em.find(PetitionRow.class, id);
		return this.current;
	}

	public PetitionRow getCurrent() {
		return current;
	}

	public void create(PetitionRow user) {
		this.em.persist(user);
		this.current = user;
	}

	public void remove(String id) {
		PetitionRow ref = this.em.getReference(PetitionRow.class, id);
		this.em.remove(ref);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save() {
		// nothing to do
	}
}
