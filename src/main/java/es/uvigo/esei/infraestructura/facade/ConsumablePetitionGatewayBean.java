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
import es.uvigo.esei.infraestructura.entities.ConsumablePetition;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ConsumablePetitionGatewayBean {
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@EJB
	private UserAuthorizationEJB auth;

	private ConsumablePetition current;

	public ConsumablePetition find(int id) {
		this.current = this.em.find(ConsumablePetition.class, id);
		return this.current;
	}

	public ConsumablePetition getCurrent() {
		return current;
	}

	public void create(ConsumablePetition petition) {
		this.em.persist(petition);
		this.current = petition;
	}

	public void remove(String id) {
		ConsumablePetition ref = this.em.getReference(ConsumablePetition.class, id);
		this.em.remove(ref);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save() {
		// nothing to do
	}
	
	public List<ConsumablePetition> getAllPetitions(){
		return em.createNamedQuery("findAllPetitions", ConsumablePetition.class).getResultList();
	}
	
	public List<ConsumablePetition> getAllDonePetitions(){
		return em.createNamedQuery("findAllDonePetitions", ConsumablePetition.class).getResultList();
	}
}
