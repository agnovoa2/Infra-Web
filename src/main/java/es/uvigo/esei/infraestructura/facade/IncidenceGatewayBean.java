package es.uvigo.esei.infraestructura.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import es.uvigo.esei.infraestructura.ejb.UserAuthorizationEJB;
import es.uvigo.esei.infraestructura.entities.Incidence;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class IncidenceGatewayBean {
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@EJB
	private UserAuthorizationEJB auth;

	private Incidence current;

	public Incidence find(int id) {
		this.current = this.em.find(Incidence.class, id);
		return this.current;
	}

	public Incidence getCurrent() {
		return current;
	}

	public void create(Incidence incidence) {
		this.em.persist(incidence);
		this.current = incidence;
	}

	public void remove(String id) {
		Incidence ref = this.em.getReference(Incidence.class, id);
		this.em.remove(ref);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save() {
		// nothing to do
	}
	
	public List<Incidence> getAll(){
		return em.createNamedQuery("findAllIncidences", Incidence.class).getResultList();
	}
}
