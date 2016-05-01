package es.uvigo.esei.infraestructura.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import es.uvigo.esei.infraestructura.ejb.UserAuthorizationEJB;
import es.uvigo.esei.infraestructura.entities.Model;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ModelGatewayBean {
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@EJB
	private UserAuthorizationEJB auth;

	private Model current;

	public Model find(String id) {
		Query query = em.createQuery("SELECT m FROM Model m WHERE m.modelName=:modelName",Model.class);
		query.setParameter("modelName", id);
		if(query.getResultList().size() == 0){
			this.current = null;
			return null;
		}
		this.current = (Model) query.getResultList().get(0);
		return current;
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
