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
import es.uvigo.esei.infraestructura.entities.Subject;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class SubjectGatewayBean {
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@EJB
	private UserAuthorizationEJB auth;

	private Subject current;

	public Subject find(String id) {
		this.current = this.em.find(Subject.class, id);
		return this.current;
	}

	public Subject getCurrent() {
		return current;
	}

	public void create(Subject subject) {
		this.em.persist(subject);
		this.current = subject;
	}

	public void remove(String id) {
		Subject ref = this.em.getReference(Subject.class, id);
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
