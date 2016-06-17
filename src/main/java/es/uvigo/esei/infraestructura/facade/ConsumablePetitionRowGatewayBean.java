package es.uvigo.esei.infraestructura.facade;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import es.uvigo.esei.infraestructura.authorization.UserAuthorizationEJB;
import es.uvigo.esei.infraestructura.entities.ConsumablePetitionRow;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ConsumablePetitionRowGatewayBean {
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@EJB
	private UserAuthorizationEJB auth;

	private ConsumablePetitionRow current;

	public ConsumablePetitionRow find(int id) {
		this.current = this.em.find(ConsumablePetitionRow.class, id);
		return this.current;
	}

	public ConsumablePetitionRow getCurrent() {
		return current;
	}

	public void create(ConsumablePetitionRow user) {
		this.em.persist(user);
		this.current = user;
	}

	public void remove(int id) {
		ConsumablePetitionRow ref = this.em.getReference(ConsumablePetitionRow.class, id);
		this.em.remove(ref);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save() {
		// nothing to do
	}
}
