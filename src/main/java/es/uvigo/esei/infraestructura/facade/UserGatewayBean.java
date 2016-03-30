package es.uvigo.esei.infraestructura.facade;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import es.uvigo.esei.infraestructura.ejb.UserAuthorizationEJB;
import es.uvigo.esei.infraestructura.entities.User;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class UserGatewayBean {
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@EJB
	private UserAuthorizationEJB auth;

	private User current;

	public User find(String id) {
		this.current = this.em.find(User.class, id);
		return this.current;
	}

	public User getCurrent() {
		return current;
	}

	public void create(User user) {
		this.em.persist(user);
		this.current = user;
	}

	public void remove(String id) {
		User ref = this.em.getReference(User.class, id);
		this.em.remove(ref);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save() {
		// nothing to do
	}

	public void update() {
		this.em.refresh(this.current);
	}

	public void removeCurrentLoad() {
		this.em.remove(this.current);
		this.current = null;
	}

	@Remove
	public void closeGateway() {

	}
}
