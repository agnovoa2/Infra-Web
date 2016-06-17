package es.uvigo.esei.infraestructura.facade;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import es.uvigo.esei.infraestructura.authorization.UserAuthorizationEJB;
import es.uvigo.esei.infraestructura.entities.Consumable;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ConsumableGatewayBean {
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@EJB
	private UserAuthorizationEJB auth;

	private Consumable current;

	public Consumable find(String id) {
		Query query = em.createQuery("SELECT c FROM Consumable c WHERE c.consumableName=:consumableName",Consumable.class);
		query.setParameter("consumableName", id);
		if(query.getResultList().size() == 0){
			this.current = null;
			return null;
		}
		this.current = (Consumable) query.getResultList().get(0);
		return current;
	}

	public Consumable getCurrent() {
		return current;
	}

	public void create(Consumable consumable) throws SQLException{
		if (this.find(consumable.getConsumableName()) == null) {
			this.em.persist(consumable);
			this.current = consumable;
		}
		else{
			throw new SQLException("Ya existe un consumible en la base de datos con ese nombre.");
		}
	}

	public void remove(int id) {
		Consumable ref = this.em.getReference(Consumable.class, id);
		this.em.remove(ref);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save() {
		// nothing to do
	}
	
	public List<Consumable> findAllConsumables(){
		return em.createNamedQuery("findAllConsumables",Consumable.class).getResultList();
	}
	
	public List<Consumable> findAllBlackConsumables(){
		return em.createNamedQuery("findAllBlackConsumables",Consumable.class).getResultList();
	}
	
	public List<Consumable> findAllPhotoBlackConsumables(){
		return em.createNamedQuery("findAllPhotoBlackConsumables",Consumable.class).getResultList();
	}
	
	public List<Consumable> findAllCyanConsumables(){
		return em.createNamedQuery("findAllCyanConsumables",Consumable.class).getResultList();
	}
	
	public List<Consumable> findAllLightCyanConsumables(){
		return em.createNamedQuery("findAllLightCyanConsumables",Consumable.class).getResultList();
	}
	
	public List<Consumable> findAllMagentaConsumables(){
		return em.createNamedQuery("findAllMagentaConsumables",Consumable.class).getResultList();
	}
	
	public List<Consumable> findAllLightMagentaConsumables(){
		return em.createNamedQuery("findAllLightMagentaConsumables",Consumable.class).getResultList();
	}
	
	public List<Consumable> findAllYellowConsumables(){
		return em.createNamedQuery("findAllYellowConsumables",Consumable.class).getResultList();
	}
	
	public List<Consumable> findAllTricolorConsumables(){
		return em.createNamedQuery("findAllTricolorConsumables",Consumable.class).getResultList();
	}
	
	public List<Consumable> findAllDrumConsumables(){
		return em.createNamedQuery("findAllDrumConsumables",Consumable.class).getResultList();
	}
	
	public List<Consumable> findAllFuserConsumables(){
		return em.createNamedQuery("findAllFuserConsumables",Consumable.class).getResultList();
	}
	
	public List<Consumable> findAllBeltUnitConsumables(){
		return em.createNamedQuery("findAllBeltUnitConsumables",Consumable.class).getResultList();
	}
	
	public List<Consumable> findAllGarbageUnitConsumables(){
		return em.createNamedQuery("findAllGarbageUnitConsumables",Consumable.class).getResultList();
	}
	
	public List<Consumable> findAllTransferKitConsumables(){
		return em.createNamedQuery("findAllTransferKitConsumables",Consumable.class).getResultList();
	}
}
