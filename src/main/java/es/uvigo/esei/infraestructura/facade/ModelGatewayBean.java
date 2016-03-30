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
import es.uvigo.esei.infraestructura.entities.Model;
import es.uvigo.esei.infraestructura.entities.Printer;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ModelGatewayBean {
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@EJB
	private UserAuthorizationEJB auth;

	private Model current;

	public Model find(String id) {
		this.current = this.em.find(Model.class, id);
		return this.current;
	}

	public Model getCurrent() {
		return current;
	}

	public void create(Model model) {
		this.em.persist(model);
		this.current = model;
	}

	public void remove(String id) {
		Model ref = this.em.getReference(Model.class, id);
		this.em.remove(ref);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save() {
		// nothing to do
	}
	
	public List<Model> getAll(){
		return em.createNamedQuery("findAllModels", Model.class).getResultList();
	}
}
